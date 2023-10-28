package com.kanwar.spring6restmvc.services;

import com.kanwar.spring6restmvc.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(UUID uuid);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(UUID uuid, Customer customer);

    void deleteCustomer(UUID uuid);

    void patchCustomer(UUID uuid, Customer customer);
}
