package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

}
