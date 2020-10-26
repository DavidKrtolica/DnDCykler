package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;


    //GET MAPPING FOR FINDING ALL CUSTOMERS
    @GetMapping("/customers")
    public ResponseEntity<List> getAllCustomers(){

            List<Customer> customers = new ArrayList<>();
            customers = customerRepository.findAll();
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    //GET MAPPING FOR FINDING A CUSTOMER BY ID
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find customer with ID = " + id));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


    //POST MAPPING FOR ADDING NEW CUSTOMER
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

            Customer newCustomer = customerRepository.save(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }


    //PUT MAPPING FOR UPDATING CUSTOMERS BY ID
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") int id, @RequestBody Customer updatedCustomer){

            Customer customer = customerRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Cannot find customer with ID = " + id));

            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setPhoneNr(updatedCustomer.getPhoneNr());
            customer.setEmail(updatedCustomer.getEmail());

            final Customer customerFinal = customerRepository.save(customer);
            return new ResponseEntity<>(customerFinal, HttpStatus.OK);
    }


    //DELETE MAPPING FOR DELETING ALL CUSTOMERS
    @DeleteMapping("/customers")
    public ResponseEntity<Customer> deleteAllCustomers(){

            List<Customer> customers = customerRepository.findAll();
            if (customers.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                customerRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.OK);
            }
    }


    //DELETE MAPPING FOR DELETING A CUSTOMER BY ITS ID
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") int id){

            Customer customer = customerRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Cannot find customer with ID = " + id));
            customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

}
