package com.ncs.fresh.cart.error;

public class CartNotFindException extends RuntimeException {
    public CartNotFindException(String message) {
        super(message);
    }
}
