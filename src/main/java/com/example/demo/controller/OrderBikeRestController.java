package com.example.demo.controller;


import com.example.demo.service.OrderBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class OrderBikeRestController {

    @Autowired
    OrderBikeService orderBikeService;

    //GET MAPPING WITH PAGINATION & SORTING TOGETHER - FINAL VERSION
    @GetMapping("/orderBike")
    public ResponseEntity<Map<String, Object>> getOrderBikePageSorted(
            @RequestParam(required = false) Integer orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId,desc") String[] sort) {
        return orderBikeService.getAllOrderBikes(orderId, page, size, sort);
    }
}