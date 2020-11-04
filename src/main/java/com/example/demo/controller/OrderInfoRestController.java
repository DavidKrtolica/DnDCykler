package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class OrderInfoRestController {

    @Autowired
    OrderInfoRepository orderRepository;

    @Autowired
    OrderBikeRepository orderBikeRepository;

    @Autowired
    CustomerRepository customerRepository;

    //METHOD RETRIEVING THE SORT.DIRECTON ENUM
    private Sort.Direction getSortDirection(String direction){
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    //GET MAPPING WITH PAGINATION & SORTING TOGETHER - FINAL VERSION
    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrdersPageSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId,desc") String[] sort){

        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){
            //WILL SORT MORE THAN 2 FIELDS, SORTORDER = "FIELD, DIRECTION"
            for (String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]),_sort[0]));
            }
        } else {
            //SORT = [FIELD, DIRECTION]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        Pageable paging = PageRequest.of(page, size, Sort.by(orders));
        Page<OrderInfo> pageOrders = orderRepository.findAll(paging);
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

        Collection<OrderBike> orderBikes = orderInfo.getOrderBikesByOrderId();
        for (OrderBike orderBike : orderBikes){
            orderBike.setOrderId(newOrderInfo.getOrderId());
            orderBikeRepository.save(orderBike);
        }

        return new ResponseEntity<>(newOrderInfo, HttpStatus.CREATED);
    }


    //DELETE MAPPING FOR DELETING ALL BIKE ORDERS
    @DeleteMapping("/orders")
    public ResponseEntity<OrderInfo> deleteAllOrders(){
            List<OrderInfo> orderInfos = orderRepository.findAll();
            if (orderInfos.isEmpty()){
                throw new ResourceNotFoundException("There are no orders!");
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

            //UPDATING THE TOTAL PRICE
            orderInfo.setTotalPrice(updatedOrderInfo.getTotalPrice());

            //UPDATING THE CUSTOMER INFO
            Customer updatedCustomer = updatedOrderInfo.getCustomerByCustomerId();
            orderInfo.setCustomerByCustomerId(updatedCustomer);

            orderRepository.save(orderInfo);

            return new ResponseEntity<>(orderInfo, HttpStatus.OK);

    }

}
