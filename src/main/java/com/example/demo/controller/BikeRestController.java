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
@RequestMapping("/api")
public class BikeRestController {

    @Autowired
    BikeRepository bikeRepository;

    //FINDING ALL BIKES, ALSO A POSSIBILITY TO FIND BY BRAND
    @GetMapping("/bikes")
    public ResponseEntity<List> getAllBikes(@RequestParam(required = false) String brand){
        try {
            List<Bike> bikes = new ArrayList<>();
            if (brand == null){
                bikes = bikeRepository.findAll();
            } else {
                bikes = bikeRepository.findByBrandContaining(brand);
            }
            if (bikes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikes, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FINDING A BIKE ID
    @GetMapping("/bikes/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") int id) {
        Optional<Bike> bikeData = bikeRepository.findById(id);
            if (bikeData.isPresent())
                return new ResponseEntity<>(bikeData.get(), HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

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
}
