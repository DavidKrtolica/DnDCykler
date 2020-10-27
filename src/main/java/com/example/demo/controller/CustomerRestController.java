package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //method retrieving the Sort.Direction enum
    private Sort.Direction getSortDirection(String direction){
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }


    //GET MAPPING FOR FINDING ALL CUSTOMERS - BASIC METHOD
    /*@GetMapping("/customers")
    public ResponseEntity<List> getAllCustomers(){

            List<Customer> customers = new ArrayList<>();
            customers = customerRepository.findAll();
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
    }*/


    //GET MAPPING & PAGINATION FOR GETTING CUSTOMERS BY PAGE
    /*@GetMapping("/customers")
    public ResponseEntity<Map<String, Object>> getPageOfCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size){

        Pageable paging = PageRequest.of(page, size);
        Page<Customer> pageCustomers = customerRepository.findAll(paging);
        List<Customer> customers = pageCustomers.getContent();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("customers", customers);
        response.put("currentPage", pageCustomers.getNumber());
        response.put("totalItems", pageCustomers.getTotalElements());
        response.put("totalPages", pageCustomers.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/


    //GET MAPPING & SORTING METHOD IMPLEMENTATION
    /*@GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomersSorted(@RequestParam(defaultValue = "customerId,desc") String[] sort){
        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){
            //will sort more than 2 fields, sortOrder="field,direction"
            for (String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]),_sort[0]));
            }
        } else {
            //sort=[field,direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Customer> customers = customerRepository.findAll(Sort.by(orders));
        if (customers.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }*/


    //GET MAPPING WITH PAGINATION & SORTING TOGETHER - FINAL VERSION
    @GetMapping("/customers")
    public ResponseEntity<Map<String, Object>> getCustomersPageSorted(
            @RequestParam(required = false) String firstName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "customerId,desc") String[] sort){

        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){
            //will sort more than 2 fields, sortOrder="field,direction"
            for (String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]),_sort[0]));
            }
        } else {
            //sort=[field,direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        Pageable paging = PageRequest.of(page, size, Sort.by(orders));

        Page<Customer> pageCustomers;
        if (firstName == null){
            pageCustomers = customerRepository.findAll(paging);
        } else {
            pageCustomers = customerRepository.findByFirstNameContaining(firstName, paging);
        }
        List<Customer> customers = pageCustomers.getContent();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("customers", customers);
        response.put("currentPage", pageCustomers.getNumber());
        response.put("totalItems", pageCustomers.getTotalElements());
        response.put("totalPages", pageCustomers.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
