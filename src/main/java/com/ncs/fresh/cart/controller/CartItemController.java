package com.ncs.fresh.cart.controller;

import com.ncs.fresh.cart.model.ApiResponse;
import com.ncs.fresh.cart.model.CartItem;
import com.ncs.fresh.cart.model.InputItemCart;
import com.ncs.fresh.cart.model.UpdateItemCart;
import com.ncs.fresh.cart.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }


    @GetMapping("/cart/{user_id}/{id}")
    public ResponseEntity<Optional<CartItem>> getUserCartById(@PathVariable String id, @PathVariable String user_id) {
        Optional<CartItem> res = this.cartItemService.getCartItemById(user_id, id);
        if (res.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/cart/{user_id}")
    public ResponseEntity<Optional<List<CartItem>>> getUserCart(@PathVariable String user_id) {
        var data = this.cartItemService.getAllCartItemByUserId(user_id);
        if (data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Optional.empty());
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping("/cart/{user_id}")
    public ResponseEntity<CartItem> createNewCart(@RequestBody InputItemCart in, @PathVariable String user_id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cartItemService.createCartItem(in, user_id));
    }

    @PutMapping("/cart/{user_id}/{id}")
    public ResponseEntity<CartItem> updateOneCartById(@PathVariable String user_id, @PathVariable String id, @RequestBody UpdateItemCart update) {
        var updatedEntity = this.cartItemService.updateCartItemById(user_id, id, update);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEntity);
    }

    @DeleteMapping("/cart/{user_id}/{id}")
    public ResponseEntity<Void> deleteCartById(@PathVariable String id, @PathVariable String user_id) {
        var result = this.cartItemService.deleteByUserAndCartId(user_id, id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


}