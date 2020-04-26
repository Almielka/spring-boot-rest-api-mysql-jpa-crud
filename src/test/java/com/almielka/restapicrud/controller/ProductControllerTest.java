package com.almielka.restapicrud.controller;

import com.almielka.restapicrud.domain.Company;
import com.almielka.restapicrud.domain.CompanyType;
import com.almielka.restapicrud.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Anna S. Almielka
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MANAGER = "MANAGER";
    private static final String CUSTOMER = "CUSTOMER";
    private static final String URL = "/api/products";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void createProduct() throws Exception {
        Company company = new Company();
        company.setName("company1-1");
        company.setCompanyType(CompanyType.TYPE1);
        Instant createdDate = Instant.now();
        Product newProduct = new Product(company, createdDate, 100);
        newProduct.setName("newProduct");
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(newProduct)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private String convertToJson(Product product) throws JsonProcessingException {
        return objectMapper.writeValueAsString(product);
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void readProductByIdSuccess() throws Exception {
        mockMvc.perform(get(URL + "/search-by-id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void readProductByIdFailed() throws Exception {
        mockMvc.perform(get(URL + "/search-by-id/99")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void deleteProductById() throws Exception {
        mockMvc.perform(delete(URL + "/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void getProductByCompanyNameSuccess() throws Exception {
        mockMvc.perform(get(URL + "/search-by-company-name/company1-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].company.name", containsString("company1-1")));
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void getProductByCompanyNameFailed() throws Exception {
        mockMvc.perform(get(URL + "/search-by-company-name/no-name")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void getProductByCompanyTypeSuccess() throws Exception {
        mockMvc.perform(get(URL + "/search-by-company-type/type2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].company.companyType", containsString("TYPE2")));
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void getProductBeforeDateSuccess() throws Exception {
        Instant instant = Instant.now();
        mockMvc.perform(get(URL + "/search-before-date?date=" + instant)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void getProductAfterDateSuccess() throws Exception {
        Instant instant = Instant.now().minus(360, ChronoUnit.DAYS);
        mockMvc.perform(get(URL + "/search-after-date?date=" + instant)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = CUSTOMER)
    public void getProductBetweenDatesSuccess() throws Exception {
        Instant afterInstant = Instant.now().minus(360, ChronoUnit.DAYS);
        Instant beforeInstant = Instant.now();
        mockMvc.perform(get(URL + "/search-between-dates?afterDate=" + afterInstant + "&beforeDate=" + beforeInstant)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}