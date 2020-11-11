package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.OrderBike;
import com.example.demo.model.OrderInfo;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderBikeRepository;
import com.example.demo.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderInfoService {

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Autowired
    OrderBikeRepository orderBikeRepository;

    @Autowired
    CustomerRepository customerRepository;


    //METHOD RETRIEVING THE SORT.DIRECTON ENUM
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    //METHOD FOR GET MAPPING TO RETRIEVE ALL ORDERS WITH PAGINATION, SORTING AND FILTERING
    public ResponseEntity<Map<String, Object>> getAllOrders(int page, int size, String[] sort) {
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
        Page<OrderInfo> pageOrders = orderInfoRepository.findAll(paging);
        List<OrderInfo> orderInfos = pageOrders.getContent();

        if (orderInfos.isEmpty()) {
            throw new ResourceNotFoundException("There are no orders!");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderInfos);
        response.put("currentPage", pageOrders.getNumber());
        response.put("totalItems", pageOrders.getTotalElements());
        response.put("totalPages", pageOrders.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //METHOD FOR GET MAPPING TO RETRIEVE ORDERS BY ID
    public ResponseEntity<OrderInfo> getOrdersById(int id) {
        OrderInfo orderInfo = orderInfoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Cannot find order with ID = " + id));
        return new ResponseEntity<>(orderInfo, HttpStatus.OK);
    }

    //METHOD FOR PUT MAPPING TO CREATE A NEW ORDER
    public ResponseEntity<OrderInfo> createNewOrder(OrderInfo orderInfo) {
        OrderInfo newOrderInfo = orderInfoRepository.save(orderInfo);

        Collection<OrderBike> orderBikes = orderInfo.getOrderBikesByOrderId();
        for (OrderBike orderBike : orderBikes) {
            orderBike.setOrderId(newOrderInfo.getOrderId());
            orderBikeRepository.save(orderBike);
        }

        return new ResponseEntity<>(newOrderInfo, HttpStatus.CREATED);
    }

    //DELETE MAPPING FOR DELETING ALL ORDERS FROM DATABASE
    public ResponseEntity<OrderInfo> deleteOrders() {
        List<OrderInfo> orderInfos = orderInfoRepository.findAll();
        if (orderInfos.isEmpty()) {
            throw new ResourceNotFoundException("There are no orders!");
        } else {
            orderInfoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //DELETE MAPPING FOR DELETING ORDERS BY ID
    public ResponseEntity<OrderInfo> deleteOrderByOrderId(int id) {
        OrderInfo orderInfo = orderInfoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Cannot find order with ID = " + id));
        orderInfoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //UPDATING / PUT MAPPING
    public ResponseEntity<OrderInfo> updateOrder(int id, OrderInfo updatedOrderInfo) {
        OrderInfo orderInfo = orderInfoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Cannot find order with ID = " + id));

        //UPDATING THE TOTAL PRICE
        orderInfo.setTotalPrice(updatedOrderInfo.getTotalPrice());

        //UPDATING THE CUSTOMER INFO
        orderInfo.setCustomerId(updatedOrderInfo.getCustomerId());

        orderInfoRepository.save(orderInfo);

        return new ResponseEntity<>(orderInfo, HttpStatus.OK);
    }

}