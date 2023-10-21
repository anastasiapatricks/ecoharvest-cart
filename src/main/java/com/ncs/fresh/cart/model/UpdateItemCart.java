package com.ncs.fresh.cart.model;

/**
 * The UpdateItemCart class is used to update the item cart.
 * It stores the product ID, an array of product IDs, an array of quantity, and the cart status.
 */
public class UpdateItemCart {

    public String cartId;
    public String productId;

    public Integer quantity;

    public CartStatusEnum status;


}
