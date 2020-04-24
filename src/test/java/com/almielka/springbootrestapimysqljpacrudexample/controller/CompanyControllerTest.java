package com.almielka.springbootrestapimysqljpacrudexample.controller;

import com.almielka.springbootrestapimysqljpacrudexample.domain.Company;
import com.almielka.springbootrestapimysqljpacrudexample.domain.CompanyType;
import com.almielka.springbootrestapimysqljpacrudexample.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
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


import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Anna S. Almielka
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MANAGER = "MANAGER";
    private static final String URL = "/api/companies";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void createCompany() throws Exception {
        Set<Product> productSet = new LinkedHashSet<>();
        Company newCompany = new Company(CompanyType.TYPE2, productSet);
        newCompany.setName("create_Company");
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(newCompany)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void updateCompanyById() throws Exception {
        Company updateCompany = new Company();
        updateCompany.setId(5L);
        updateCompany.setName("update_Company");
        updateCompany.setCompanyType(CompanyType.TYPE3);
        mockMvc.perform(put(URL + "/{id}" , updateCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(updateCompany)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updateCompany.getId()))
                .andExpect(jsonPath("name").value(updateCompany.getName()));
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void updateCompanyByName() throws Exception {
        Company updateCompany = new Company();
        updateCompany.setId(1L);
        updateCompany.setName("company1-1");
        updateCompany.setCompanyType(CompanyType.TYPE3);
        mockMvc.perform(put(URL + "/name/{name}" , updateCompany.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(updateCompany)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updateCompany.getId()))
                .andExpect(jsonPath("name").value(updateCompany.getName()));
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void readCompanyByIdSuccess() throws Exception {
        mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void readCompanyByIdFailed() throws Exception {
        mockMvc.perform(get(URL + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void readCompanyByNameSuccess() throws Exception {
        mockMvc.perform(get(URL + "/name/company")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", containsString("company")));
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void readCompanyByNameFailed() throws Exception {
        mockMvc.perform(get(URL + "/name/no-name")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void deleteCompanyById() throws Exception {
        mockMvc.perform(delete(URL + "/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = MANAGER)
    public void deleteCompanyByName() throws Exception {
        mockMvc.perform(delete(URL + "/name/company3-2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String convertToJson(Company company) throws JsonProcessingException {
        return objectMapper.writeValueAsString(company);
    }

}