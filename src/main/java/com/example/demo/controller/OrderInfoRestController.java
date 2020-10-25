package com.example.demo.controller;

import com.example.demo.model.OrderInfo;
import com.example.demo.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderInfoRestController {

    @Autowired
    OrderInfoRepository orderRepository;

    //GET MAPPING FOR FINDING ALL BIKE ORDERS
    @GetMapping("/orderInfos")
    public ResponseEntity<List> getAllOrders(){
        try {
            List<OrderInfo> orderInfos = new ArrayList<>();
            orderInfos = orderRepository.findAll();
            if (orderInfos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderInfos, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GET MAPPING FOR FINDING ORDERS BY ID
    @GetMapping("/orderInfos/{id}")
    public ResponseEntity<OrderInfo> getOrderById(@PathVariable("id") int id){
        try {
            Optional<OrderInfo> orderData = orderRepository.findById(id);
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
    @PostMapping("/orderInfos")
    public ResponseEntity<OrderInfo> createOrder(@RequestBody OrderInfo orderInfo){
        try {
            OrderInfo newOrderInfo = orderRepository.save(orderInfo);
            return new ResponseEntity<>(newOrderInfo, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE MAPPING FOR DELETING ALL BIKE ORDERS
    @DeleteMapping("/orderInfos")
    public ResponseEntity<OrderInfo> deleteAllOrders(){
        try {
            List<OrderInfo> orderInfos = orderRepository.findAll();
            if (orderInfos.isEmpty()){
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
    @DeleteMapping("/orderInfos/{id}")
    public ResponseEntity<OrderInfo> deleteOrderById(@PathVariable("id") int id){
        try {
            Optional<OrderInfo> orderData = orderRepository.findById(id);
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
    @PutMapping("/orderInfos/{id}")
    public ResponseEntity<OrderInfo> updateBikeOrderById(@PathVariable("id") int id, @RequestBody OrderInfo updatedOrderInfo){
        try {
            OrderInfo orderInfo = orderRepository.findById(id).get();
            orderInfo.setCustomerId(updatedOrderInfo.getCustomerId());
            orderInfo.setTotalPrice(updatedOrderInfo.getTotalPrice());
            final OrderInfo orderInfoFinal = orderRepository.save(orderInfo);
            return new ResponseEntity<>(orderInfoFinal, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
