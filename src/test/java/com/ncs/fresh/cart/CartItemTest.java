package com.ncs.fresh.cart;

import com.ncs.fresh.cart.model.CartItem;
import com.ncs.fresh.cart.model.CartStatusEnum;
import com.ncs.fresh.cart.model.InputItemCart;
import com.ncs.fresh.cart.repository.CartItemRepositoryInterface;
import com.ncs.fresh.cart.service.CartItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncs.fresh.cart.util.MockCartDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
class CartItemTest {
    @Autowired
    private CartItemService cartItemService;

    private static final Random RANDOM = new Random();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final CartItemRepositoryInterface repository;

    @Autowired
    public CartItemTest(CartItemRepositoryInterface repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setUp() {
//        List<InputItemCart> cart = MockCartDataGenerator.generateMockCarts(50);
//        for (InputItemCart inputItemCart : cart) {
//            String userId = "user" + (RANDOM.nextInt(5) + 1);
//            CartItem i = new CartItem(inputItemCart, userId);
//            this.repository.save(i);
//        }

    }

    @Test
    public void testGetUserCartById() throws Exception {
        String userid = "user1";
        String cartId = "6501bd184758327589111a28";
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/cart/%s/%s", userid, cartId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").value(cartId))
                .andDo(print());
    }

    @Test
    public void testGetAllUserCart() throws Exception {
        String userid = "user1";

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/cart/%s", userid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value(userid))
                .andDo(print());
    }

    @Test
    public void testCreateNewCart() throws Exception {
        String userId = "123";
        String[] products = new String[1];
        products[0] = "123";

        Integer[] quantities = new Integer[1];
        quantities[0] = 5;

        InputItemCart mockData = new InputItemCart(products, quantities);

        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/cart/%s", userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockData)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").exists())
                .andDo(print());


    }

    @Test
    public void testUpdateCart() throws Exception {
        String userId = "123";
        String[] products = new String[1];
        products[0] = "123";

        Integer[] quantities = new Integer[1];
        quantities[0] = 5;

        InputItemCart mockData = new InputItemCart(products, quantities);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(String.format("/cart/%s", userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockData)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").exists()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();

        CartItem addedCart = objectMapper.readValue(responseBody, CartItem.class);

        addedCart.status = CartStatusEnum.CANCELED;

        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/cart/%s/%s", userId, addedCart.cartId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addedCart)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("CANCELED"))
                .andDo(print());
    }

    @Test
    public void testDeleteCartById() throws Exception {
        String userId = "123";
        String[] products = new String[1];
        products[0] = "123";

        Integer[] quantities = new Integer[1];
        quantities[0] = 5;

        InputItemCart mockData = new InputItemCart(products, quantities);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(String.format("/cart/%s", userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockData)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").exists()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();

        CartItem addedCart = objectMapper.readValue(responseBody, CartItem.class);


        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/cart/%s/%s", userId, addedCart.cartId))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());


    }
}
