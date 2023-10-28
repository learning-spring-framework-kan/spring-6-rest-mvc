package com.kanwar.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanwar.spring6restmvc.domain.Customer;
import com.kanwar.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @Captor
    ArgumentCaptor<Customer> customerCaptor;

    Customer testCustomer = Customer.builder()
            .id(UUID.randomUUID())
            .name("Test Singh")
            .build();

    @Test
    void testGetCustomerById() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willReturn(testCustomer);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",is(testCustomer.getName())));
    }

    @Test
    void testGetCustomers() throws Exception {

        List<Customer> customerList = new ArrayList<>();
        customerList.add(testCustomer);
        given(customerService.getAllCustomers()).willReturn(customerList);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(jsonPath("$[0].name", is(testCustomer.getName())));
    }

    @Test
    void testCreateCustomer() throws Exception {
        given(customerService.createCustomer(any(Customer.class))).willReturn(testCustomer);

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUdpateCustomer() throws Exception {
        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomer(any(UUID.class),any(Customer.class));
    }

    @Test
    void testDeleteCustomer() throws Exception{
        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(customerService).deleteCustomer(uuidCaptor.capture());

        assertThat(testCustomer.getId()).isEqualTo(uuidCaptor.getValue());


    }

    @Test
    void testPatchCustomer() throws Exception {

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "new Name");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomer(uuidCaptor.capture(),customerCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(testCustomer.getId());
        assertThat(customerCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));
    }
}