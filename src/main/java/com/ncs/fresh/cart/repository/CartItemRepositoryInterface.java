package com.ncs.fresh.cart.repository;

import com.ncs.fresh.cart.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryInterface extends MongoRepository<CartItem, String> {

    @Query(value = "{ 'userId' : ?0, '_id': ?1 }")
    Optional<CartItem> getByUserIdAndProductsId(String UserId, String productsId);


    @Query(value = "{ 'userId' : ?0 }")
    Optional<List<CartItem>> getByUserId(String UserId);

    @Query(value = "{ 'userId' : ?0, '_id': ?1 }", delete = true)
    void deleteByUserIdAndProductsId(String UserId, String cartId);
}
