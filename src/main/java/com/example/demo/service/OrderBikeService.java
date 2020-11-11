package com.example.demo.service;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderBikeService {

    @Autowired
    OrderBikeRepository orderBikeRepository;

    //METHOD RETRIEVING THE SORT.DIRECTON ENUM
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    //METHOD FOR RETRIEVING ALL ORDER-BIKES
    public ResponseEntity<Map<String, Object>> getAllOrderBikes(Integer orderId, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            //WILL SORT MORE THAN 2 FIELDS, SORTORDER = "FIELD, DIRECTION"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            //SORT = [FIELD, DIRECTION]
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
