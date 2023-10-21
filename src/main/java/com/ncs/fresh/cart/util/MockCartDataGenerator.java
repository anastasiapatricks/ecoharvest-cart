//package com.ncs.fresh.cart.util;
//
//
//import com.ncs.fresh.cart.model.InputItemCart;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class MockCartDataGenerator {
//
//    private static final Random RANDOM = new Random();
//
//    public static List<InputItemCart> generateMockCarts(int numberOfCarts) {
//        List<InputItemCart> carts = new ArrayList<>();
//
//        for (int i = 0; i < numberOfCarts; i++) {
//            InputItemCart cart = new InputItemCart(generateRandomProductIds(), generateRandomQuantities());
//
//            carts.add(cart);
//        }
//
//        return carts;
//    }
//
//    public static String generateRandomUserId() {
//        return "user" + (RANDOM.nextInt(5) + 1); // Generates user1 to user5
//    }
//
//    private static String[] generateRandomProductIds() {
//        int numProducts = RANDOM.nextInt(5) + 1; // Generates 1 to 5 products per cart
//        String[] productId = new String[numProducts];
//
//        for (int i = 0; i < numProducts; i++) {
//            productId[i] = "product" + (i + 1);
//        }
//
//        return productId;
//    }
//
//    private static Integer[] generateRandomQuantities() {
//        int numProducts = RANDOM.nextInt(5) + 1;
//        Integer[] quantity = new Integer[numProducts];
//
//        for (int i = 0; i < numProducts; i++) {
//            quantity[i] = RANDOM.nextInt(10) + 1; // Generates random quantity between 1 and 10
//        }
//
//        return quantity;
//    }
//
//    public static void main(String[] args) {
//        List<InputItemCart> mockCarts = generateMockCarts(5);
//
//        for (InputItemCart cart : mockCarts) {
//            System.out.println(cart);
//        }
//    }
//}
