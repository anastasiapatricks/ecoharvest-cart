package com.ncs.fresh.cart.model;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InputItemCart {

    public String[] productIds;
    public Integer[] quantities;

    public InputItemCart(@JsonProperty("productIds") String[] productIds, @JsonProperty("quantities") Integer[] quantities) {
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
