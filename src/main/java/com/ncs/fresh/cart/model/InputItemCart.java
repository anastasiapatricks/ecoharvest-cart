package com.ncs.fresh.cart.model;

import java.util.Arrays;

public class InputItemCart {


    public final String[] productIds;
    public final Integer[] quantities;

    public InputItemCart(String[] productIds, Integer[] quantities) {
        this.productIds = productIds;
        this.quantities = quantities;
    }

    @Override
    public String toString() {
        return "InputItemCart{" +
                ", productIds=" + Arrays.toString(productIds) +
                ", quantities=" + Arrays.toString(quantities) +
                '}';
    }
}
