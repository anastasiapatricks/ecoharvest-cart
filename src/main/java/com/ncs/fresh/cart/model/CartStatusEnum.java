package com.ncs.fresh.cart.model;

public enum CartStatusEnum {
    EMPTY("Empty"),
    ACTIVE("Active"),
    CHECKOUT("Checkout"),
    PAID("Paid"),
    CANCELED("Canceled");

    private final String status;

    CartStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
