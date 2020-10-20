package com.example.demo.controller;

import com.example.demo.model.BikeOrder;
import com.example.demo.repository.BikeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BikeOrderRestController {

    @Autowired
    BikeOrderRepository bikeOrderRepository;

    @GetMapping("/bikeOrders")
    public ResponseEntity<List> getAllBikeOrders(){
        try {
            List<BikeOrder> bikeOrders = new ArrayList<>();
            bikeOrders = bikeOrderRepository.findAll();
            if (bikeOrders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikeOrders, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
