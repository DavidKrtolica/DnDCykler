package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BikeRestController {

    @Autowired
    BikeRepository bikeRepository;

    //FINDING ALL BIKES
    @GetMapping("/bikes")
    public ResponseEntity<List> getAllBikes(){
        try {
            List<Bike> bikes = new ArrayList<>();
                bikes = bikeRepository.findAll();
            if (bikes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikes, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FINDING A BIKE BY ID
    @GetMapping("/bikes/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") int id) {
        Optional<Bike> bikeData = bikeRepository.findById(id);
        if (bikeData.isPresent())
            return new ResponseEntity<>(bikeData.get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //FIND BY BRAND
    @GetMapping({"/bikeByBrand", "/bikeBrand"})
    public ResponseEntity<List> getAllBikesWithBrand(@RequestParam(required = true) String brand) {
        try {
            List<Bike> bikes = new ArrayList<>();

            bikes = bikeRepository.findByBrandContaining(brand);

            if (bikes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikes, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FIND BY TYPE
    @GetMapping({"/bikeByType", "/bikeType"})
    public ResponseEntity<List> getAllBikesWithType(@RequestParam(required = true) String type) {
        try {
            List<Bike> bikes = new ArrayList<>();

            bikes = bikeRepository.findByTypeContaining(type);

            if (bikes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikes, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FIND BY STATE
    @GetMapping({"/bikeByState", "/bikeState"})
    public ResponseEntity<List> getAllBikesWithState(@RequestParam(required = true) String state) {
        try {
            List<Bike> bikes = new ArrayList<>();

            bikes = bikeRepository.findByStateContaining(state);

            if (bikes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikes, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FIND BY FRAME SIZE
    @GetMapping({"/bikeByFrameSize", "/bikeFrameSize"})
    public ResponseEntity<List> getAllBikesWithFrameSize(@RequestParam(required = true) String frameSize) {
        try {
            List<Bike> bikes = new ArrayList<>();

            bikes = bikeRepository.findByFrameSizeContaining(frameSize);

            if (bikes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikes, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FIND BY PRICE -> PRICE RANGE (FROM MIN TO MAX)

    //ADDING NEW BIKE
    @PostMapping("/bikes")
    public ResponseEntity<Bike> createBike(@RequestBody Bike bike) {
        try {
            Bike newBike = bikeRepository.save(bike);
            return new ResponseEntity<>(newBike, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //UPDATING A BIKE

    //DELETING A BIKE

    //PAGINATION

    //SORT A-Z

    //SORT Z-A
}
