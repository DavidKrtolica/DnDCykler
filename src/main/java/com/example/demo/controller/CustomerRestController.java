package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.model.Customer;
import com.example.demo.model.OrderInfo;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    //GET MAPPING FOR FINDING ALL CUSTOMERS
    @GetMapping("/customers")
    public ResponseEntity<List> getAllCustomers(){
        try {
            List<Customer> customers = new ArrayList<>();
            customers = customerRepository.findAll();
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FINDING A CUSTOMER BY ID
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {

        Optional<Customer> customerData = customerRepository.findById(id);

        if (customerData.isPresent()) {
            return new ResponseEntity<>(customerData.get(),HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //ADDING NEW CUSTOMER
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer newCustomer = customerRepository.save(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //UPDATING CUSTOMERS BY ID
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") int id, @RequestBody Customer updatedCustomer){
        try {
            Customer customer = customerRepository.findById(id).get();
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setPhoneNr(updatedCustomer.getPhoneNr());
            customer.setEmail(updatedCustomer.getEmail());
            final Customer customerFinal = customerRepository.save(customer);
            return new ResponseEntity<>(customerFinal, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE MAPPING FOR DELETING ALL CUSTOMERS
    @DeleteMapping("/customers")
    public ResponseEntity<Customer> deleteAllCustomers(){
        try {
            List<Customer> customers = customerRepository.findAll();
            if (customers.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                customerRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE MAPPING FOR DELETING A CUSTOMER BY ITS ID
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") int id){
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isPresent()){
                customerRepository.deleteById(customer.get().getCustomerId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
