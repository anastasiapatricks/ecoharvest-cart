package com.ncs.fresh.cart.service;

import com.ncs.fresh.cart.error.CartNotFindException;
import com.ncs.fresh.cart.error.InternalServerErrorException;
import com.ncs.fresh.cart.model.CartItem;
import com.ncs.fresh.cart.model.InputItemCart;
import com.ncs.fresh.cart.model.UpdateItemCart;
import com.ncs.fresh.cart.repository.CartItemRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private static final Logger log = LoggerFactory.getLogger(CartItemService.class);
    private final CartItemRepositoryInterface repository;

    @Autowired
    public CartItemService(CartItemRepositoryInterface repository) {
        this.repository = repository;
    }

    @Autowired
    private MongoTemplate mongoTemplate;


    public CartItem createCartItem(InputItemCart in, String user_id) {
        log.info("Create New Cart user");
        try {
//            Find if there cart with the same product id and user id
            log.debug("Find Existing Product with user");
            var res = this.repository.getCartWithProductItem(user_id, in.productId);

            if (res.isPresent()) {
                log.debug("Existing Product exist, adding the value");
                var cart = res.get();
                cart.quantity += in.quantity;
                this.repository.save(cart);
                return cart;
            } else {
                log.debug("Product not found creating new cart");
                CartItem cart = new CartItem(in, user_id);
                log.debug(cart.toString());
                cart = this.repository.save(cart);
                System.out.println("hello");
                log.debug("Saved Cart Successfully with ID: " + cart.cartId);
                return cart;
            }

        } catch (Exception e) {
            log.error("Internal Exception on createCartItem", e);
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    public Optional<CartItem> getCartItemById(String user_id, String cart_id) {
        log.info("Get Cart Item by Id");
        try {

            log.debug("Getting one cart item");
            var cartItem = this.repository.getByUserIdAndProductsId(user_id, cart_id);

            if (cartItem.isEmpty()) {
                log.debug("Not found any result");
                return Optional.empty();
            } else {
                log.debug("Found one cart item");
                log.debug(cartItem.get().toString());

                return cartItem;
            }
        } catch (Exception e) {
            log.error("Internal Exception on getCartItemById", e);
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    public Optional<List<CartItem>> getAllCartItemByUserId(String user_id) {
        log.info("Get All user cart");
        try {
            log.debug("Getting User cart " + user_id);
            var data = this.repository.getByUserId(user_id);
            log.debug("Retrieved use cart");
            log.debug("Returning");
            return data;

        } catch (Exception e) {
            log.error("Internal Exception on getAllCartItemByUserId");
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    public List<CartItem> updateCartItems(String user_id, List<UpdateItemCart> updateCart) {
        log.info("Updating Cart Item");
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, CartItem.class);
        try {
            log.debug("Update cart data :" + updateCart.toString());
            updateCart.forEach(cart -> {
                Query query = Query.query(Criteria.where("_id").is(cart.cartId));
                Update update = new Update();
                update.set("productId", cart.productId);
                update.set("quantity", cart.quantity);
                update.set("updatedDate", LocalDateTime.now());
                bulkOperations.updateOne(query, update);
            });
            bulkOperations.execute();

            log.debug("Update complete successfully");
            log.debug("Retrieve the updated value");
            Query query = new Query(Criteria.where("userId").is(user_id));
            List<CartItem> result = mongoTemplate.find(query, CartItem.class);
            if (result.isEmpty()) {
                log.debug("Empty result");
            } else {
                log.debug("Found Total Cart Item: " + result.size());
            }
            return result;

        } catch (Exception e) {
            log.error("Internal Exception on updateCartItems");
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    public boolean deleteByUserAndCartId(String user_id, String cartId) {
        log.info("deleting Cart Id");
        try {
            log.debug(String.format("Deleting Cart By ID %s user ID %s", cartId, user_id));
            var cart = this.getCartItemById(user_id, cartId);
            if (cart.isEmpty()) {
                return false;
            }
            log.debug("Cart found, deleting the cart");
            this.repository.delete(cart.get());
            return true;
        } catch (Exception e) {
            log.error("Internal Exception on deleteByUserAndCartId");
            throw new InternalServerErrorException("Internal Server Error");
        }
    }


}
