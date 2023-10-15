package com.ncs.fresh.cart.error;


public class CartQuantitiesProductMismatchException extends RuntimeException {
    public CartQuantitiesProductMismatchException(String message) {
        super(message);
    }
}