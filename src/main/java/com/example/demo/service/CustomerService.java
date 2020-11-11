package com.example.demo.service;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    //METHOD RETRIEVING THE SORT.DIRECTON ENUM
    private Sort.Direction getSortDirection(String direction){
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    //METHOD USED FOR GET MAPPING TO RETRIEVE ALL CUSTOMERS FROM THE DATABASE, IMPLEMENTED PAGINATION, FILTERING AND SORTING
    public ResponseEntity<Map<String, Object>> getAllCustomers(String firstName, int size, String[] sort, int page) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){
            //WILL SORT MORE THAN 2 FIELDS, SORTORDER = "FIELD, DIRECTION"
            for (String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]),_sort[0]));
            }
        } else {
            //SORT = [FIELD, DIRECTION]
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
            throw new ResourceNotFoundException("There are no customers!");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("customers", customers);
        response.put("currentPage", pageCustomers.getNumber());
        response.put("totalItems", pageCustomers.getTotalElements());
        response.put("totalPages", pageCustomers.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //METHOD USED FOR GET MAPPING TO RETRIEVE ONE CUSTOMER BY HIS ID
    public ResponseEntity<Customer> getCustomerByCustomerId(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find customer with ID = " + id));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    //METHOD FOR CREATING A NEW CUSTOMER - POST MAPPING
    public ResponseEntity<Customer> createNewCustomer(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    //METHOD FOR PUT MAPPING - UPDATING EXISTING CUSTOMER INFO
    public ResponseEntity<Customer> updateCustomer(int id, Customer updatedCustomer) {
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

    //METHOD FOR DELETE MAPPING - DELETING ALL CUSTOMERS
    public ResponseEntity<Customer> deleteCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()){
            throw new ResourceNotFoundException("There are no customers!");
        } else {
            customerRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //METHOD FOR DELETE MAPPING - BY CUSTOMER ID
    public ResponseEntity<Customer> deleteCustomerByCustomerId(int id) {
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Cannot find customer with ID = " + id));
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
