package com.example.demo.controller;

import com.example.demo.exception.MethodNotAllowedException;
import com.example.demo.exception.ResourceNotFoundException;
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
            // INITIALIZE BOOLEAN AND TEMPORARY INT VARIABLE
            boolean condition = false;
            int temp = 0;

            // INITIALIZE ARRAYLIST AND POPULATE WITH ENTITIES FROM DATABASE
            List<Bike> bikes = new ArrayList<>();
            bikes = bikeRepository.findAll();

            for (int i = 0; i < bikes.size(); i++) {

                temp = bikes.get(i).getBikeId();

                if (id == temp) {
                    condition = true;
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
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

        //DELETING A BIKE BY ID
    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<Bike> deleteBikeById(@PathVariable("id") int id) {
        Optional<Bike> bike = bikeRepository.findById(id);
        // CHECKS IF THE ID IS FOUND - IF NOT, THROWS AN EXCEPTION
        if (!bike.isPresent()) {
            throw new ResourceNotFoundException("Bike with written ID doesn't exist. Please, try again.");
        }
        // DELETE FOUND BIKE
        bikeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE ALL BIKES
    @DeleteMapping("/bikes")
    public ResponseEntity<Bike> deleteAllBikes() {
        List<Bike> bikes = bikeRepository.findAll();
        if (bikes.isEmpty()) {
            throw new MethodNotAllowedException("There are 0 Bikes. You can not delete.");
        } else {
            bikeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //PAGINATION
    @GetMapping("/bikes")
    public ResponseEntity<Map<String, Object>> getAllBikes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "brand") String filter,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(required = false) String parameter,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max)

    {
        try {
            // INITIALIZE AND POPULATE AN ARRAYLIST FOR ALL BIKES,ALSO INITIALIZE AN ARRAYLIST FOR PAGED BIKES
            List<Bike> sortedBikesFromDB = new ArrayList<>();
            List<Bike> bikes = new ArrayList<>();
            List<Bike> pagedBikes = new ArrayList<>();

            // BEGINNING OF SEARCHING BY PARAMETER VALUE
            // IT WILL GO INTO FIRST IF-STATEMENT, AND IF THE PARAMETER VALUE DOESN'T MATCH TO THE ANY OF THE VALUES IN THE BIKE TABLE FOR ATTRIBUTE "type"
            // IT WILL NOT POPULATE OUR ARRAYLIST, THEN IT WILL GO TO THE NEXT IF STATEMENT BECAUSE "size" REMAINED 0 AND "parameter" IS STILL EQUAL TO "null"
            // IT WILL TRY OUT DIFFERENT IF-STATEMENTS UNTIL ONE PARAMETER VALUE MATCHES ATTRIBUTE VALUE OR THERE ARE NO MATCHES SO IT WILL JUST THROW NOTHING FOUND ERROR
            // CHECKS IF THE PARAMETER IS OF VALUE "type"
            if (parameter != null && sortedBikesFromDB.size() == 0) {
                sortedBikesFromDB = bikeRepository.findByTypeContaining(parameter);
            }
            // CHECKS IF THE PARAMETER IS OF VALUE "state"
            if (parameter != null && sortedBikesFromDB.size() == 0) {
                sortedBikesFromDB = bikeRepository.findByStateContaining(parameter);
            }
            // CHECKS IF THE PARAMETER IS OF VALUE "brand"
            if (parameter != null && sortedBikesFromDB.size() == 0) {
                sortedBikesFromDB = bikeRepository.findByBrandContaining(parameter);
            }
            // CHECKS IF THE PARAMETER IS OF VALUE "frameSize"
            if (parameter != null && sortedBikesFromDB.size() == 0) {
                sortedBikesFromDB = bikeRepository.findByFrameSizeContaining(parameter);
            }

            // IF NO PARAMETER WAS INPUTTED, THEN PASS ALL THE BICYCLES IN CASE OF SELECTING THE PRICE RANGE
            if (parameter == null && sortedBikesFromDB.size() == 0) {
                sortedBikesFromDB = bikeRepository.findAll();;
            }

            // MIN & MAX - PRICE RANGE
            if (min != null && max != null) {
                // CHECKS THE PRICE OF THE BICYCLE AND ADDS IT TO THE ARRAY WHICH CONSISTS OF ALL BICYCLES THAT PASS THE MIN-MAX TEST
                int priceTest;
                for (int i = 0; i < sortedBikesFromDB.size(); i++) {
                    priceTest = sortedBikesFromDB.get(i).getPrice();
                    if (priceTest >= min && priceTest <= max) {
                        bikes.add(sortedBikesFromDB.get(i));
                    }
                }
            }

            bikes = sortedBikesFromDB;

            // IN CASE SOMETHING WENT WRONG WITH DATABASE CONNECTION OR THERE IS NO BIKE ENTITIES IN THE DATABASE
            if (bikes.isEmpty()) {
                throw new ResourceNotFoundException("There are no Bikes.");
            }

            // USE THIS TO CHECK THE INPUTTED PAGE AND SIZE
            // EXAMPLE: We have 8 objects in our database. We want to see 6 objects per page. That means, there are 2 pages.
            // The 1. page has 6 objects. The 2. page has 2 objects. There should not be page number greater then 2 then.
            int number = bikes.size() / size;

            // IF-STATEMENT TO CHECK THE ENTERED PAGE AND SIZE
            if (page >= 0 && page <= number) {
                // INITIALIZE THE VARIABLES THAT WILL BE NEEDED THROUGHOUT THE METHOD RUN
                // EXAMPLE: If we want to see items on the 0. page, we should start from the 1. item
                // until the 6. item.(6 items per page)
                int start = page * size;        // start = 0 * 6; -> 0  STARTING POINT
                int end = (page + 1) * size;    // end = (0 + 1) * 6; -> 6 ENDING POINT

                // INITIALIZE THE VARIABLES THAT WE WILL NEED IN THE FOR-LOOP, BUT FOR THE LAST PAGE
                // (IF THERE IS NO CORRESPONDING NUMBER OF OBJECTS) ON THE LAST OBJECT
                // EXAMPLE: There are 8 objects in the database. The 1. page has 6 objects. The 2. page has 2 objects.
                int lastPageStart = bikes.size() - end;        // For page 0, result is 2 (8 - 6). For page 1, result is -4 (8 - 12).
                int lastPageEnd = bikes.size();              // This will always stop the last page.

                // FOR-LOOP TO POPULATE AN ARRAY THAT WILL BE PUSHED AS A PAGE
                for (int n = start; n < end; n++) {
                    // IF-STATEMENT THAT WILL BREAK WHEN THE LAST ITEM ON THE LAST PAGE IS GOTTEN
                    if (lastPageStart < 0 && n == lastPageEnd) {
                        break;
                    }
                    pagedBikes.add(bikes.get(n));
                }

                // SORTING METHOD WHICH SORTS BY TYPE, STATE, BRAND, FRAME SIZE OR PRICE
                switch (filter) {
                    case "type":
                        if (sort.equals("asc")) {
                            Collections.sort(pagedBikes, Bike::compareToByType);
                        } else {
                            Collections.sort(pagedBikes, Bike::compareToByType);
                            Collections.reverse(pagedBikes);
                        }
                    break;

                    case "state":
                        if (sort.equals("asc")) {
                            Collections.sort(pagedBikes, Bike::compareToByState);
                        } else {
                            Collections.sort(pagedBikes, Bike::compareToByState);
                            Collections.reverse(pagedBikes);
                        }
                    break;

                    case "brand":
                        if (sort.equals("asc")) {
                            Collections.sort(pagedBikes, Bike::compareToByBrand);
                        } else {
                            Collections.sort(pagedBikes, Bike::compareToByBrand);
                            Collections.reverse(pagedBikes);
                        }
                    break;

                    case "frameSize":
                        if (sort.equals("asc")) {
                            Collections.sort(pagedBikes, Bike::compareToByFrameSize);
                        } else if (sort.equals("desc")) {
                            Collections.sort(pagedBikes, Bike::compareToByFrameSize);
                            Collections.reverse(pagedBikes);
                        }
                    break;

                    case "price":
                        if (sort.equals("asc")) {
                            Collections.sort(pagedBikes, Bike::compareToByPrice);
                        } else if (sort.equals("desc")) {
                            Collections.sort(pagedBikes, Bike::compareToByPrice);
                            Collections.reverse(pagedBikes);
                        }
                    break;
                }

                // MAP WHICH REPRESENTS VALUES AND IS RETURNED
                Map<String, Object> response = new HashMap<>();
                response.put("Bikes", pagedBikes);
                response.put("currentPage", page);
                response.put("totalItems", bikes.size());
                response.put("totalPages", number);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            throw new ResourceNotFoundException("Oops, something went wrong. There are no Bikes that match your criteria.");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}