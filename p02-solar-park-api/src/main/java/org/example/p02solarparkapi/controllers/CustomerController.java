package org.example.p02solarparkapi.controllers;

import org.example.p02solarparkapi.entities.Customer;
import org.example.p02solarparkapi.http.AppResponse;
import org.example.p02solarparkapi.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer) {

        HashMap<String, Object> response = new HashMap<>();

        if(this.customerService.createCustomer(customer)) {

            return AppResponse.success()
                    .withMessage("Customer created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Customer could not be created")
                .build();
    }

    @GetMapping("/customers")
    public ResponseEntity<?> fetchAllCustomers() {

        ArrayList<Customer> collection = (ArrayList<Customer>) this.customerService.getAllCustomers();

        return AppResponse.success()
                .withData(collection)
                .build();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> fetchCustomer(@PathVariable int id) {
        Customer customer = this.customerService.GetById(id);

        if(customer == null) {
            return AppResponse.error()
                    .withMessage("Customer not found!")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(customer)
                .build();
    }

    @PutMapping("/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        boolean isUpdateSuccessful = this.customerService.updateCustomer(customer);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Customer not found!")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Update successful!")
                .build();
    }
}
