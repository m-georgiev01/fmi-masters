package org.example.controllers;

import org.example.stereotypes.*;

@Controller(method = "GET", endpoint = "/customer")
public class CustomerController {
    @GetMapping("/customer")
    public String fetchAllCustomers() {
        return "Customer info - GET Request";
    }

    @GetMapping("/customer/{id}/projects/{project_id}")
    public String fetchCustomerById(@PathVariable("id") int id,
                                    @PathVariable("project_id") int projectId) {
        return "Customer Info - GET Request By Id " + id + " with project id " + projectId;
    }

    @PostMapping("/customer")
    public String createCustomer() {
        return "Customer info - POST Request";
    }

    @PutMapping("/customer")
    public String updateCustomer() {
        return "Customer info - PUT Request";
    }

    @DeleteMapping("/customer")
    public String deleteCustomer() {
        return "Customer info - DELETE Request";
    }
}
