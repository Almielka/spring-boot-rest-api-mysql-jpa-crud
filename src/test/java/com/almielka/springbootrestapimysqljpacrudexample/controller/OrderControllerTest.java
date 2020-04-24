package com.almielka.springbootrestapimysqljpacrudexample.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Anna S. Almielka
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @Autowired
    private MockMvc mockMvc;

    private static final String CUSTOMER = "CUSTOMER";
    private static final String URL = "/api/products/order";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

//    @Test
//    @WithMockUser(roles = CUSTOMER)
//    public void createOrder() throws Exception {
//        String productListJson = "[{\"id\":3},\n" +
//                "{\"id\": 4}]";
//        mockMvc.perform(post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(productListJson))
//                .andDo(print())
//                .andExpect(status().isCreated());
//    }


    @Test
    @WithMockUser(roles = CUSTOMER)
    public void findAllOrders() throws Exception {
        mockMvc.perform(get(URL + "/history")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void findAllOrderedProducts() throws Exception {
        mockMvc.perform(get(URL + "/history-of-the-most-ordered-products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4));
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void findMostOrderedCompanies() throws Exception {
        mockMvc.perform(get(URL + "/history-of-the-most-ordered-companies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", containsString("company4-3")));
    }
}