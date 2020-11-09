package com.example.demo;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bike;
import com.example.demo.model.Customer;
import com.example.demo.model.OrderBike;
import com.example.demo.model.OrderInfo;
import com.example.demo.repository.BikeRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderBikeRepository;
import com.example.demo.repository.OrderInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnitTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Autowired
    OrderBikeRepository orderBikeRepository;


    // TESTS METHOD FOR FINDING CUSTOMER BY ID
    @Test
    public void returnsCustomer() {
        customerRepository.deleteAll();
        Optional<Customer> customer = customerRepository.findById(2);
        assert(!customer.isPresent());      // TEST WILL FAIL BECAUSE TEST IS DONE ON EMPTY DB
    }

    // TESTS METHOD FOR FINDING BIKE BY ID
    @Test
    public void returnsBike() {
        bikeRepository.deleteAll();
        Optional<Bike> bike = bikeRepository.findById(4);
        assert(!bike.isPresent());
    }

    // TESTS METHOD FOR FINDING ORDER BY ID
    @Test
    public void returnsOrderInfo() {
        orderInfoRepository.deleteAll();
        Optional<OrderInfo> orderInfo = orderInfoRepository.findById(3);
        assert(!orderInfo.isPresent());
    }

    // TESTS METHOD FOR CREATING A NEW BIKE
    @Test
    public void creatingNewBike() {
        bikeRepository.deleteAll();
        Bike testBike = new Bike();
        testBike.setType("TEST");
        testBike.setState("TEST");
        testBike.setBrand("TEST");
        testBike.setFrameSize("TEST");
        testBike.setPrice(2222);
        bikeRepository.save(testBike);
        assert(!bikeRepository.findAll().isEmpty());
    }

    // TESTS METHOD FOR CREATING A NEW CUSTOMER
    @Test
    public void creatingNewCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setFirstName("TEST");
        testCustomer.setLastName("TEST");
        testCustomer.setAddress("TEST");
        testCustomer.setPhoneNr("TEST");
        testCustomer.setEmail("TEST");

        Customer newCustomer = customerRepository.save(testCustomer);

        boolean condition = false;

        List<Customer> customersForTesting = customerRepository.findAll();

        for (int i = 0; i < customersForTesting.size(); i++) {
            if (customersForTesting.get(i).getFirstName().equals("TEST") ||
            customersForTesting.get(i).getLastName().equals("TEST") ||
            customersForTesting.get(i).getAddress().equals("TEST") ||
            customersForTesting.get(i).getPhoneNr().equals("TEST") ||
            customersForTesting.get(i).getEmail().equals("TEST")) {

                condition = true;
            }
        }
        assert(condition);
    }


    // TESTS METHOD FOR DELETING ALL CUSTOMERS
    @Test
    public void returnsDeleteAllCustomers() {
        customerRepository.deleteAll();
        assert(customerRepository.findAll().isEmpty());
    }

    // TESTS METHOD FOR DELETING ALL BIKES
    @Test
    public void returnsDeleteAllBikes() {
        bikeRepository.deleteAll();
        assert(bikeRepository.findAll().isEmpty());
    }

    // TESTS METHOD DELETING ALL ORDERS
    @Test
    public void returnsDeleteAllOrderInfo() {
        orderInfoRepository.deleteAll();
        assert(orderInfoRepository.findAll().isEmpty());
    }

    // TESTS METHOD FOR UPDATING CUSTOMER
    @Test
    public void returnsUpdateCustomer() {
        Customer initialCustomer = new Customer("David", "David", "CPH", "12341234", null);
        customerRepository.save(initialCustomer);

        Customer customer = customerRepository.findById(initialCustomer.getCustomerId()).
                orElseThrow(() -> new ResourceNotFoundException("Not found"));

        customer.setFirstName("NAME");
        customer.setLastName("SURNAME");
        customer.setAddress("ADDRESS");
        customer.setPhoneNr("PHONE NUMBER");
        customer.setEmail("EMAIL");

        final Customer fCustomer = customerRepository.save(customer);

        assert (fCustomer.getFirstName()).equals(customer.getFirstName());
        assert (fCustomer.getAddress()).equals(customer.getAddress());
    }

    // TESTS METHOD FOR UPDATING A BIKE
    @Test
    public void returnsUpdateBike() {
        Bike initialBike = new Bike("Race", "New", "GT", "XL", 6000);
        bikeRepository.save(initialBike);

        Bike bike = bikeRepository.findById(initialBike.getBikeId()).
                orElseThrow(() -> new ResourceNotFoundException("Not found"));

        bike.setType("TYPE");
        bike.setState("STATE");
        bike.setBrand("BRAND");
        bike.setFrameSize("FRAME SIZE");
        bike.setPrice(12000);

        final Bike fBike = bikeRepository.save(bike);

        assert (fBike.getType().equals(bike.getType()));
        assert (fBike.getPrice() == bike.getPrice());
    }

}
