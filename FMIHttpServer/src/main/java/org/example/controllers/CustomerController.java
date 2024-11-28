package org.example.controllers;

import org.example.services.CustomerService;
import org.example.stereotypes.*;
import org.example.system.ResponseMessage;

@Controller(method = "GET", endpoint = "/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public String fetchAllCustomers() {
        return this.customerService.hello();
    }

    @GetMapping("/customer/{id}/projects/{project_id}")
    public ResponseMessage fetchCustomerById(@PathVariable("id") int id,
                                             @PathVariable("project_id") int projectId) {
        return new ResponseMessage(
                "Single Customer Info - " + id + " with project id " + projectId,
                404
        );
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
