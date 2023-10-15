package com.ncs.fresh.cart.model;


import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document("cart")
public class CartItem {

    @MongoId
    public String cartId;

    @CreatedBy
    public String userId;


    public String[] productIds;

    public Integer[] quantities;

    @CreatedDate
    public Date createdDate;
    @LastModifiedDate
    public Date updatedDate;

    public CartStatusEnum status;

    public CartItem() {
    }

    @JsonIgnore
    public CartItem(InputItemCart in, String userId) {
        this.userId = userId;
        this.productIds = in.productIds;
        this.quantities = in.quantities;
        this.createdDate = new Date();
        this.updatedDate = new Date();
        this.status = CartStatusEnum.ACTIVE;
    }

    @JsonCreator
    public CartItem(String cartId, String userId, String[] productIds, Integer[] quantities, Date createdDate, Date updatedDate, CartStatusEnum status) {
        this.cartId = cartId;
        this.userId = userId;
        this.productIds = productIds;
        this.quantities = quantities;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
    }


//    public static CartItem createNewRepo(InputItemCart in){
//
//
//
//    }


//    public static CartItem generateFakeCartItem() {
//        CartItem cartItem = new CartItem();
//
//        // Generate a random cart ID (you can use UUID for a more realistic approach)
//        cartItem.cartId = "cart_" + new Random().nextInt(1000);
//
//        // Generate a random user ID
//        cartItem.userId = "user_" + new Random().nextInt(1000);
//
//        // Generate random product IDs and quantities (you can modify this based on your data source)
//        int numProducts = new Random().nextInt(5) + 1; // Random number of products between 1 and 5
//        cartItem.productIds = new String[numProducts];
//        cartItem.quantities = new String[numProducts];
//        for (int i = 0; i < numProducts; i++) {
//            cartItem.productIds[i] = "product_" + new Random().nextInt(1000);
//            cartItem.quantities[i] = String.valueOf(new Random().nextInt(10) + 1); // Random quantity between 1 and 10
//        }
//
//        // Set created and updated dates to the current date and time
//        cartItem.createdDate = new Date();
//        cartItem.updatedDate = new Date();
//
//        return cartItem;
//    }

}
