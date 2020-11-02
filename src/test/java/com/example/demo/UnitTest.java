package com.example.demo;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bike;
import com.example.demo.model.Customer;
import com.example.demo.model.OrderInfo;
import com.example.demo.repository.BikeRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UnitTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Test
    public void returnsCustomer1() {
        Optional<Customer> customer = customerRepository.findById(2);
        assert(customer.isPresent());
    }

    @Test
    public void returnsBike() {
        Optional<Bike> bike = bikeRepository.findById(4);
        assert(bike.isPresent());
    }

    // NEED TO CREATE ORDER OBJECT IN THE DB
    @Test
    public void returnsOrderInfo() {
        Optional<OrderInfo> orderInfo = orderInfoRepository.findById(3);
        assert(orderInfo.isPresent());
    }

    @Test
    public void returnsDeleteAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        customerRepository.deleteAll();

        assert(customers.size() == 0);
    }

    @Test
    public void returnsDeleteAllBikes() {
        List<Bike> bikes = bikeRepository.findAll();
        bikeRepository.deleteAll();

        assert(bikes.size() == 0);
    }

    @Test
    public void returnsDeleteAllOrderInfo() {
        List<OrderInfo> orderInfos = orderInfoRepository.findAll();
        orderInfoRepository.deleteAll();

        assert(orderInfos.size() == 0);
    }

    @Test
    public void returnsUpdateCustomer() {
        Customer customer = customerRepository.findById(1).
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

    @Test
    public void returnsUpdateBike() {
        Bike bike = bikeRepository.findById(1).
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
