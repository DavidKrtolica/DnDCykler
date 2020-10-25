package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderRestController {

    @Autowired
    OrderRepository orderRepository;

    //GET MAPPING FOR FINDING ALL BIKE ORDERS
    @GetMapping("/orders")
    public ResponseEntity<List> getAllOrders(){
        try {
            List<Order> orders = new ArrayList<>();
            orders = orderRepository.findAll();
            if (orders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GET MAPPING FOR FINDING ORDERS BY ID
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int id){
        try {
            Optional<Order> orderData = orderRepository.findById(id);
            if (orderData.isPresent()){
                return new ResponseEntity<>(orderData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ADDING A NEW BIKE ORDER - POST MAPPING
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        try {
            Order newOrder = orderRepository.save(order);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE MAPPING FOR DELETING ALL BIKE ORDERS
    @DeleteMapping("/orders")
    public ResponseEntity<Order> deleteAllOrders(){
        try {
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                orderRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE MAPPING FOR DELETING AN ORDER BY ITS ID
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Order> deleteOrderById(@PathVariable("id") int id){
        try {
            Optional<Order> orderData = orderRepository.findById(id);
            if (orderData.isPresent()){
                orderRepository.deleteById(orderData.get().getOrderId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //UPDATING BIKE ORDERS BY ID
    @PutMapping("/bikeOrders/{id}")
    public ResponseEntity<Order> updateBikeOrderById(@PathVariable("id") int id, @RequestBody Order updatedOrder){
        try {
            Order order = orderRepository.findById(id).get();
            order.setCustomerId(updatedOrder.getCustomerId());
            order.setTotalPrice(updatedOrder.getTotalPrice());
            final Order orderFinal = orderRepository.save(order);
            return new ResponseEntity<>(orderFinal, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
