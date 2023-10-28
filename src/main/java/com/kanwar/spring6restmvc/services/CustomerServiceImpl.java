package com.kanwar.spring6restmvc.services;

import com.kanwar.spring6restmvc.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customersMap;

    public CustomerServiceImpl() {
        this.customersMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .name("customer1")
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version("1")
                .build();

        Customer customer2 = Customer.builder()
                .name("customer2")
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version("2")
                .build();

        Customer customer3 = Customer.builder()
                .name("customer3")
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version("3")
                .build();

        this.customersMap.put(customer1.getId(),customer1);
        this.customersMap.put(customer2.getId(),customer2);
        this.customersMap.put(customer3.getId(),customer3);
    }


    @Override
    public List<Customer> getAllCustomers() {
        log.info("  --CustomerServiceImpl::getAllCustomers--");
        return new ArrayList<>(customersMap.values());
    }

    @Override
    public Customer getCustomerById(UUID uuid) {
        log.info("  --CustomerServiceImpl::getCustomerById--");
        return customersMap.get(uuid);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        log.info("  --CustomerServiceImpl::createCustomer--");
        Customer newCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .name(customer.getName())
                .build();

        customersMap.put(newCustomer.getId(),newCustomer);
        return newCustomer;
    }

    @Override
    public Customer updateCustomer(UUID uuid, Customer customer) {
        log.info("  --CustomerServiceImpl::updateCustomer--");
        Customer updatedCustomer = customersMap.get(uuid);
        updatedCustomer.setCreatedDate(LocalDateTime.now());
        updatedCustomer.setLastModifiedDate(LocalDateTime.now());
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setVersion("Updated");
        customersMap.put(updatedCustomer.getId(),updatedCustomer);
        return updatedCustomer;
    }

    @Override
    public void deleteCustomer(UUID uuid) {
        log.info("  --CustomerServiceImpl::deleteCustomer--");
        customersMap.remove(uuid);
    }

    @Override
    public void patchCustomer(UUID uuid, Customer customer) {
        log.info("  --CustomerServiceImpl::patchCustomer--");
        Customer patchedCustomer = customersMap.get(uuid);
        if(!customer.getName().isEmpty()) patchedCustomer.setName(customer.getName());
        patchedCustomer.setVersion("Updated");
        customersMap.put(patchedCustomer.getId(),patchedCustomer);
    }
}
