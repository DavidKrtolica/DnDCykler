package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class CustomerRestController {

    @Autowired
    CustomerService customerService;


    //GET MAPPING WITH PAGINATION, FILTERING & SORTING TOGETHER - FINAL VERSION
    @GetMapping("/customers")
    public ResponseEntity<Map<String, Object>> getCustomersPageSorted(
            @RequestParam(required = false) String firstName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "customerId,desc") String[] sort){
        return customerService.getAllCustomers(firstName, size, sort, page);
    }


    //GET MAPPING FOR FINDING A CUSTOMER BY ID
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        return customerService.getCustomerByCustomerId(id);
    }


    //POST MAPPING FOR ADDING NEW CUSTOMER
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return customerService.createNewCustomer(customer);
    }


    //PUT MAPPING FOR UPDATING CUSTOMERS BY ID
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") int id, @RequestBody Customer updatedCustomer){
        return customerService.updateCustomer(id, updatedCustomer);
    }


    //DELETE MAPPING FOR DELETING ALL CUSTOMERS
    @DeleteMapping("/customers")
    public ResponseEntity<Customer> deleteAllCustomers(){
        return customerService.deleteCustomers();
    }


    //DELETE MAPPING FOR DELETING A CUSTOMER BY ITS ID
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") int id){
        return customerService.deleteCustomerByCustomerId(id);
    }

}
