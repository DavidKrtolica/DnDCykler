package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BikeRestController {

    @Autowired
    BikeService bikeService;


    //ADDING NEW BIKE
    @PostMapping("/bikes")
    public ResponseEntity<Bike> createBike(@RequestBody Bike bike) {
        return bikeService.createBike(bike);
    }


    //UPDATING A BIKE BY ID
    @PutMapping("/bikes/{id}")
    public ResponseEntity<Bike> updateBikeById(@PathVariable("id") int id, @RequestBody Bike updatedBike) {
        return bikeService.updateBike(id, updatedBike);

    }


    //DELETING A BIKE BY ID
    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<Bike> deleteBikeById(@PathVariable("id") int id) {
        return bikeService.deleteBikeByBikeId(id);
    }



    //DELETE ALL BIKES
    @DeleteMapping("/bikes")
    public ResponseEntity<Bike> deleteAllBikes() {
        return bikeService.deleteBikes();
    }


    //GET ALL BIKES FROM DATABASE
    @GetMapping("/bikes")
    public ResponseEntity<Map<String, Object>> getAllBikes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "bikeId") String filter,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(required = false) String parameter,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {
        return bikeService.getBikes(page, size, filter, sort, parameter, min, max);
    }
}