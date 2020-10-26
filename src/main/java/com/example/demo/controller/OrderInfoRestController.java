package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.model.OrderInfo;
import com.example.demo.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class OrderInfoRestController {

    @Autowired
    OrderInfoRepository orderRepository;

    //GET MAPPING FOR FINDING ALL BIKE ORDERS
    /*@GetMapping("/orders")
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
    }*/

    //method retrieving the Sort.Direction enum
    private Sort.Direction getSortDirection(String direction){
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrdersPageSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "orderId,desc") String[] sort){

        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){
            //will sort more than 2 fields, sortOrder="field,direction"
            for (String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]),_sort[0]));
            }
        } else {
            //sort=[field,direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        Pageable paging = PageRequest.of(page, size, Sort.by(orders));
        Page<OrderInfo> pageOrders = orderRepository.findAll(paging);
        List<OrderInfo> orderInfos = pageOrders.getContent();

        if (orderInfos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderInfos);
        response.put("currentPage", pageOrders.getNumber());
        response.put("totalItems", pageOrders.getTotalElements());
        response.put("totalPages", pageOrders.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //GET MAPPING FOR FINDING ORDERS BY ID
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderInfo> getOrderById(@PathVariable("id") int id) {
        OrderInfo orderInfo = orderRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Cannot find order with ID = " + id));
        return new ResponseEntity<>(orderInfo, HttpStatus.OK);

    }

    //ADDING A NEW BIKE ORDER - POST MAPPING
    @PostMapping("/orders")
    public ResponseEntity<OrderInfo> createOrder(@RequestBody OrderInfo orderInfo){
            OrderInfo newOrderInfo = orderRepository.save(orderInfo);
            return new ResponseEntity<>(newOrderInfo, HttpStatus.CREATED);
    }

    //DELETE MAPPING FOR DELETING ALL BIKE ORDERS
    @DeleteMapping("/orders")
    public ResponseEntity<OrderInfo> deleteAllOrders(){
            List<OrderInfo> orderInfos = orderRepository.findAll();
            if (orderInfos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                orderRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.OK);
            }
    }

    //DELETE MAPPING FOR DELETING AN ORDER BY ITS ID
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<OrderInfo> deleteOrderById(@PathVariable("id") int id){
            OrderInfo orderInfo = orderRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Cannot find order with ID = " + id));
            orderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    //UPDATING BIKE ORDERS BY ID
    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderInfo> updateOrderById(@PathVariable("id") int id, @RequestBody OrderInfo updatedOrderInfo){
            OrderInfo orderInfo = orderRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Cannot find order with ID = " + id));
            orderInfo.setCustomerId(updatedOrderInfo.getCustomerId());
            orderInfo.setTotalPrice(updatedOrderInfo.getTotalPrice());
            final OrderInfo orderInfoFinal = orderRepository.save(orderInfo);
            return new ResponseEntity<>(orderInfoFinal, HttpStatus.OK);

    }

}
