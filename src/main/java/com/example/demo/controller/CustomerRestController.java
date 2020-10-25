package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

}
