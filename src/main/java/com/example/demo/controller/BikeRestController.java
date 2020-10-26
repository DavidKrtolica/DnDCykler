package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BikeRestController {

    @Autowired
    BikeRepository bikeRepository;

    /*
    //FINDING ALL BIKES
    @GetMapping("/bikes")
    public ResponseEntity<List> getAllBikes() {
        try {
            List<Bike> bikes = new ArrayList<>();
            bikes = bikeRepository.findAll();
            if (bikes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FINDING A BIKE BY ID
    @GetMapping("/bikes/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") int id) {

        Optional<Bike> bikeData = bikeRepository.findById(id);

        if (bikeData.isPresent()) {
            return new ResponseEntity<>(bikeData.get(),HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    @GetMapping({"/bikeByPriceRange", "/bikePriceRange"})
    public ResponseEntity<List> getAllBikesByPriceRange(@RequestParam int min, int max){
        try {
            List<Bike> bikes = new ArrayList<>();
            List<Bike> bikesInRange = new ArrayList<>();
            int priceTest;
            bikes = bikeRepository.findAll();
            for (int i = 0; i < bikes.size(); i++){
                priceTest = bikes.get(i).getPrice();
                if (priceTest >= min && priceTest <= max){
                    bikesInRange.add(bikes.get(i));
                }
            }
            if (bikesInRange.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bikesInRange, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     */

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

    //UPDATING A BIKE BY ID
    @PutMapping("/bikes/{id}")
    public ResponseEntity<Bike> updateBikeById(@PathVariable("id") int id, @RequestBody Bike updatedBike) {
        try {
            // Initialize boolean variable and temporary int variable
            boolean condition = false;
            int temp = 0;

            // Initialize ArrayList and populate with entities from database
            List<Bike> bikes = new ArrayList<>();
            bikes = bikeRepository.findAll();

            for (int i = 0; i < bikes.size(); i++) {

                temp = bikes.get(i).getBikeId();

                if(id == temp) {
                    condition=true;
                }
            }

            if (condition = true) {
                Bike bike = bikeRepository.findById(id).get();

                // Set attributes for the updated bike
                bike.setType(updatedBike.getType());
                bike.setState(updatedBike.getState());
                bike.setBrand(updatedBike.getBrand());
                bike.setFrameSize(updatedBike.getFrameSize());
                bike.setPrice(updatedBike.getPrice());

                final Bike bikeFinal = bikeRepository.save(bike);

                return new ResponseEntity<>(bikeFinal, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETING A BIKE BY ID
    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<Bike> deleteBikeById(@PathVariable("id") int id) {
        try {
            // Initialize boolean variable and temporary int variable
            boolean condition = false;
            int temp = 0;
            int ID = 0;

            // Initialize ArrayList and populate with entities from database
            List<Bike> bikes = new ArrayList<>();
            bikes = bikeRepository.findAll();

            for (int i = 0; i < bikes.size(); i++) {

                temp = bikes.get(i).getBikeId();

                if(id == temp) {
                    ID = temp;
                    condition=true;
                }
            }

            if (condition = true) {
                bikeRepository.deleteById(ID);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE ALL BIKES
    @DeleteMapping("/bikes")
    public ResponseEntity<Bike> deleteAllBikes() {
        try {
            List<Bike> bikes = bikeRepository.findAll();
            if (bikes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                bikeRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //PAGINATION
    @GetMapping("/bikes")
    public ResponseEntity<Map<String, Object>> getAllBikes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "brand") String filter,
            @RequestParam(defaultValue = "desc") String sort) {
        try {
            // INITIALIZE AND POPULATE ARRAYLIST FOR ALL BIKES, INITIALIZE ARRAYLIST FOR PAGED BIKES
            List<Bike> bikes = new ArrayList<>();
            List<Bike> pagedBikes = new ArrayList<>();
            bikes = bikeRepository.findAll();
            
            int number = bikes.size() / size;

            // IF THE CORRECT NUMBER OF PAGE IS ENTERED, IT STARTS THE LOOP
            if (page >= 0 && page <= number) {
                int start = page * size;
                ;
                int end = (page + 1) * size;

                // POPULATE NEW ARRAY WHICH IS PUSHED AS PAGE
                for (int n = start; n < end; n++) {
                    pagedBikes.add(bikes.get(n));
                }

                // SORTING METHOD BY TYPE, STATE, BRAND, FRAME SIZE OR PRICE
                if (filter.equals("type")) {
                    if (sort.equals("asc")) {
                        Collections.sort(pagedBikes, Bike::compareToByType);
                    } else {
                        Collections.sort(pagedBikes, Bike::compareToByType);
                        Collections.reverse(pagedBikes);
                    }

                } else if (filter.equals("state")){
                    if (sort.equals("asc")) {
                        Collections.sort(pagedBikes, Bike::compareToByState);
                    } else {
                        Collections.sort(pagedBikes, Bike::compareToByState);
                        Collections.reverse(pagedBikes);
                    }

                } else if (filter.equals("brand")) {
                    if (sort.equals("asc")) {
                        Collections.sort(pagedBikes, Bike::compareToByBrand);
                    } else {
                        Collections.sort(pagedBikes, Bike::compareToByBrand);
                        Collections.reverse(pagedBikes);
                    }

                } else if(filter.equals("frameSize")) {
                    if (sort.equals("asc")) {
                        Collections.sort(pagedBikes, Bike::compareToByFrameSize);
                    } else if (sort.equals("desc")) {
                        Collections.sort(pagedBikes, Bike::compareToByFrameSize);
                        Collections.reverse(pagedBikes);
                    }
                }

                // NEED TO DEFINE "compareToWithPrice" METHOD IN THE "Bike.java" CLASS
                /*
                 else if(filter.equals("price")) {

                }
                */

                // MAP WHICH REPRESENTS VALUE IN THE RETURNED QUERY
                Map<String, Object> response = new HashMap<>();
                response.put("Bikes", pagedBikes);
                response.put("currentPage", page);
                response.put("totalItems", bikes.size());
                response.put("totalPages", number);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}