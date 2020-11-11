package com.example.demo.controller;

import com.example.demo.model.OrderInfo;
import com.example.demo.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class OrderInfoRestController {

    @Autowired
    OrderInfoService orderInfoService;


    //GET MAPPING WITH PAGINATION & SORTING TOGETHER - FINAL VERSION
    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrdersPageSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId,desc") String[] sort){
        return orderInfoService.getAllOrders(page, size, sort);
    }


    //GET MAPPING FOR FINDING ORDERS BY ID
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderInfo> getOrderById(@PathVariable("id") int id) {
        return orderInfoService.getOrdersById(id);
    }


    //ADDING A NEW BIKE ORDER - POST MAPPING
    @PostMapping("/orders")
    public ResponseEntity<OrderInfo> createOrder(@RequestBody OrderInfo orderInfo){
        return orderInfoService.createNewOrder(orderInfo);
    }


    //DELETE MAPPING FOR DELETING ALL BIKE ORDERS
    @DeleteMapping("/orders")
    public ResponseEntity<OrderInfo> deleteAllOrders(){
        return orderInfoService.deleteOrders();
    }


    //DELETE MAPPING FOR DELETING AN ORDER BY ITS ID
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<OrderInfo> deleteOrderById(@PathVariable("id") int id){
        return orderInfoService.deleteOrderByOrderId(id);
    }


    //UPDATING BIKE ORDERS BY ID
    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderInfo> updateOrderById(@PathVariable("id") int id, @RequestBody OrderInfo updatedOrderInfo){
        return orderInfoService.updateOrder(id, updatedOrderInfo);
    }
}
