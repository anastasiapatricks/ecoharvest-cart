package com.ncs.fresh.cart.service;

import com.ncs.fresh.cart.error.CartNotFindException;
import com.ncs.fresh.cart.error.CartQuantitiesProductMismatchException;
import com.ncs.fresh.cart.error.InternalServerErrorException;
import com.ncs.fresh.cart.model.CartItem;
import com.ncs.fresh.cart.model.InputItemCart;
import com.ncs.fresh.cart.model.UpdateItemCart;
import com.ncs.fresh.cart.repository.CartItemRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public CartItem createCartItem(InputItemCart in, String user_id) {
        log.info("Create New Cart user");
        try {
            log.debug("Check if the cart has equal amount of products and quantities");
            if (in.productIds.length != in.quantities.length) {
                throw new CartQuantitiesProductMismatchException("Cart count mismatch");
            }
            CartItem cart = new CartItem(in, user_id);
            log.debug(cart.toString());
            cart = this.repository.save(cart);
            System.out.println("hello");
            log.debug("Saved Cart Successfully with ID: " + cart.cartId);
            return cart;
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
            log.error("Internal Exception on getAllCartItemByUserId", e);
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    public CartItem updateCartItemById(String user_id, String cartId, UpdateItemCart update) {
        log.info("Updating Cart Item");
        try {
            log.debug(String.format("Updating Cart Item of %s user ID %s", cartId, user_id));
            log.debug("Retrieving user cart");
            var cart = this.getCartItemById(user_id, cartId);
            if (cart.isEmpty()) {
                log.warn(String.format("Cart not found for of %s user ID %s", cartId, user_id));
                throw new CartNotFindException("Cart not found");
            }
            CartItem c = cart.get();
            if (update.productIds.length != update.quantities.length) {
                log.warn("Product quantity and quantity length not same");
                throw new CartQuantitiesProductMismatchException("Mismatch Product quantity");
            }
            c.productIds = update.productIds;
            c.quantities = update.quantities;
            c.status = update.status;
            log.debug("Updating the cart");
            var updated = this.repository.save(c);
            log.debug("Cart has been updated");
            log.debug(updated.toString());
            return updated;

        } catch (Exception e) {
            log.error("Internal Exception on updateCartItemById", e);
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
            log.error("Internal Exception on deleteByUserAndCartId", e);
            throw new InternalServerErrorException("Internal Server Error");
        }
    }



}
