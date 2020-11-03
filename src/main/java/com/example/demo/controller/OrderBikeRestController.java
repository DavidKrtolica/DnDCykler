package com.example.demo.controller;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.OrderBike;
import com.example.demo.repository.OrderBikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class OrderBikeRestController {

    @Autowired
    OrderBikeRepository orderBikeRepository;

    //method retrieving the Sort.Direction enum
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    //GET MAPPING WITH PAGINATION & SORTING TOGETHER - FINAL VERSION
    @GetMapping("/orderBike")
    public ResponseEntity<Map<String, Object>> getOrderBikePageSorted(
            @RequestParam(required = false) Integer orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId,desc") String[] sort) {

        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            //will sort more than 2 fields, sortOrder="field,direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            //sort=[field,direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        Pageable paging = PageRequest.of(page, size, Sort.by(orders));
        Page<OrderBike> pageOrderBike;

        if (orderId == null){
            pageOrderBike = orderBikeRepository.findAll(paging);
        } else {
            pageOrderBike = orderBikeRepository.findByOrderId(orderId, paging);
        }

        if(pageOrderBike.isEmpty()){
            throw new ResourceNotFoundException("There are no bike orders!");
        }
        List<OrderBike> orderBikes = pageOrderBike.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderBikes);
        response.put("currentPage", pageOrderBike.getNumber());
        response.put("totalItems", pageOrderBike.getTotalElements());
        response.put("totalPages", pageOrderBike.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}