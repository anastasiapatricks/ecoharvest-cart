package com.ncs.fresh.cart.model;

import java.util.Arrays;

/**
 * The UpdateItemCart class is used to update the item cart.
 * It stores the product ID, an array of product IDs, an array of quantities, and the cart status.
 */
public class UpdateItemCart {

    public String[] productIds;

    public Integer[] quantities;

    public CartStatusEnum status;

    @Override
    public String toString() {
        return "UpdateItemCart{" +
                ", productIds=" + Arrays.toString(productIds) +
                ", quantities=" + Arrays.toString(quantities) +
                ", status=" + status +
                '}';
    }
}
