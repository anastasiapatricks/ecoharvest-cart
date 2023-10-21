package com.ncs.fresh.cart.model;

import java.util.Arrays;

public class InputItemCart {


    public final String productId;
    public final Integer quantity;

    public InputItemCart() {
        this.productId = "";
        this.quantity = 0;
    }

    public InputItemCart(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "InputItemCart{" +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
