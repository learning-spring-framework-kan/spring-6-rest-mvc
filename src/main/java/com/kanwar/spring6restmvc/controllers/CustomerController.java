package com.kanwar.spring6restmvc.controllers;


import com.kanwar.spring6restmvc.domain.Customer;
import com.kanwar.spring6restmvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/customer";

    public static final String CUSTOMER_PATH_ID = "/api/customer"+"/{customerId}";

    @GetMapping(CUSTOMER_PATH)
    public ResponseEntity<List<Customer>> getAllCustomers(){
        log.info("--CustomerController::getAllCustomers");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getAllCustomers());
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") UUID customerId){
        log.info("--CustomerController::getCustomerById");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerById(customerId));
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        log.info("--CustomerController::createCustomer");

        Customer createdCustomer = customerService.createCustomer(customer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("location",createdCustomer.getId().toString())
                .body(createdCustomer);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer){
        log.info("--CustomerController::updateCustomer");

        Customer updatedCustomer = customerService.updateCustomer(customerId,customer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomer);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") UUID customerId){
        log.info("--CustomerController::deleteCustomer");

        customerService.deleteCustomer(customerId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> patchCustomer(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer){
        log.info("--CustomerController::patchCustomer");

        customerService.patchCustomer(customerId,customer);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}


