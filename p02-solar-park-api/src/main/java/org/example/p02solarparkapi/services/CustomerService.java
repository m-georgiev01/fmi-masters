package org.example.p02solarparkapi.services;

import org.example.p02solarparkapi.entities.Customer;
import org.example.p02solarparkapi.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean createCustomer(Customer customer) {
        return this.customerRepository.create(customer);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.fetchAll();
    }

    public Customer GetById(int id) {
        return this.customerRepository.fetch(id);
    }

    public boolean updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    public boolean removeCustomer(int id){
        return this.customerRepository.delete(id);
    }
}
