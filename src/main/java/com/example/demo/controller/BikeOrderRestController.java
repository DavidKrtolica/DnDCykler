package com.example.demo.controller;

import com.example.demo.model.BikeOrder;
import com.example.demo.repository.BikeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BikeOrderRestController {

    @Autowired
    BikeOrderRepository bikeOrderRepository;

    //GET MAPPING FOR FINDING ALL BIKE ORDERS
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

    //GET MAPPING FOR FINDING ORDERS BY ID
    @GetMapping("/bikeOrders/{id}")
    public ResponseEntity<BikeOrder> getBikeOrderById(@PathVariable("id") int id){
        try {
            Optional<BikeOrder> bikeOrderData = bikeOrderRepository.findById(id);
            if (bikeOrderData.isPresent()){
                return new ResponseEntity<>(bikeOrderData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ADDING A NEW BIKE ORDER - POST MAPPING
    @PostMapping("/bikeOrders")
    public ResponseEntity<BikeOrder> createBikeOrder(@RequestBody BikeOrder bikeOrder){
        try {
            BikeOrder newBikeOrder = bikeOrderRepository.save(bikeOrder);
            return new ResponseEntity<>(newBikeOrder, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE MAPPING FOR DELETING ALL BIKE ORDERS
    @DeleteMapping("/bikeOrders")
    public ResponseEntity<BikeOrder> deleteAllBikeOrders(){
        try {
            List<BikeOrder> bikeOrders = bikeOrderRepository.findAll();
            if (bikeOrders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                bikeOrderRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE MAPPING FOR DELETING AN ORDER BY ITS ID
    @DeleteMapping("/bikeOrders/{id}")
    public ResponseEntity<BikeOrder> deleteBikeOrderById(@PathVariable("id") int id){
        try {
            Optional<BikeOrder> bikeOrderData = bikeOrderRepository.findById(id);
            if (bikeOrderData.isPresent()){
                bikeOrderRepository.deleteById(bikeOrderData.get().getOrderId());
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
    public ResponseEntity<BikeOrder> updateBikeOrderById(@PathVariable("id") int id, @RequestBody BikeOrder updatedBikeOrder){
        try {
            BikeOrder bikeOrder = bikeOrderRepository.findById(id).get();
            bikeOrder.setBikeId(updatedBikeOrder.getBikeId());
            bikeOrder.setOrderPrice(updatedBikeOrder.getOrderPrice());
            final BikeOrder bikeOrderFinal = bikeOrderRepository.save(bikeOrder);
            return new ResponseEntity<>(bikeOrderFinal, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
