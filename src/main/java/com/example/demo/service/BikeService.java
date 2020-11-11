package com.example.demo.service;

import com.example.demo.exception.MethodNotAllowedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BikeService {

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

    //METHOD FOR CREATING A BIKE - POST MAPPING
    public ResponseEntity<Bike> createBike(Bike bike) {
        Bike newBike = bikeRepository.save(bike);
        return new ResponseEntity<>(newBike, HttpStatus.CREATED);
    }

    // METHOD FOR UPDATING A BIKE BY ID - PUT MAPPING
    public ResponseEntity<Bike> updateBike(int id, Bike updatedBike) {
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
        } else {
            throw new ResourceNotFoundException("Cannot find bike you want to update!");
        }
    }

    //METHOD FOR DELETING ONE BIKE BY ITS ID - DELETE MAPPING
    public ResponseEntity<Bike> deleteBikeByBikeId(int id) {
        Optional<Bike> bike = bikeRepository.findById(id);
        // CHECKS IF THE ID IS FOUND - IF NOT, THROWS AN EXCEPTION
        if (!bike.isPresent()) {
            throw new ResourceNotFoundException("Bike with written ID doesn't exist. Please, try again.");
        }
        // DELETE FOUND BIKE
        bikeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //METHOD FOR DELETING ALL BIKES
    public ResponseEntity<Bike> deleteBikes() {
        List<Bike> bikes = bikeRepository.findAll();
        if (bikes.isEmpty()) {
            throw new MethodNotAllowedException("There are 0 Bikes. You can not delete.");
        } else {
            bikeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //METHOD FOR SHOWING ALL BIKES - GET MAPPING, OUR APPROACH FOR FILTERING, PAGINATION AND SORTING
    public ResponseEntity<Map<String, Object>> getBikes(int page, int size, String filter, String sort, String parameter, Integer min, Integer max) {
        // INITIALIZE AND POPULATE AN ARRAYLIST FOR ALL BIKES,ALSO INITIALIZE AN ARRAYLIST FOR PAGED BIKES
        List<Bike> sortedBikesFromDB = new ArrayList<>();
        List<Bike> bikes = new ArrayList<>();
        List<Bike> pagedBikes = new ArrayList<>();

        // BEGINNING OF SEARCHING BY PARAMETER VALUE
        // IT WILL GO INTO FIRST IF-STATEMENT, AND IF THE PARAMETER VALUE DOESN'T MATCH TO THE ANY OF THE VALUES IN THE BIKE TABLE FOR ATTRIBUTE "type"
        // IT WILL NOT POPULATE OUR ARRAYLIST, THEN IT WILL GO TO THE NEXT IF STATEMENT BECAUSE "size" REMAINED 0 AND "parameter" IS STILL EQUAL TO "null"
        // IT WILL TRY OUT DIFFERENT IF-STATEMENTS UNTIL ONE PARAMETER VALUE MATCHES ATTRIBUTE VALUE OR THERE ARE NO MATCHES SO IT WILL JUST THROW NOTHING FOUND ERROR
        // CHECKS IF THE PARAMETER IS OF VALUE "type"

        // CHECKS IF THE PARAMETER IS OF VALUE "frameSize"
        if (parameter != null && parameter.length() < 4 && sortedBikesFromDB.size() == 0) {
            sortedBikesFromDB = bikeRepository.findByFrameSizeContaining(parameter);
        }

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

        // IF NO PARAMETER WAS INPUTTED, THEN PASS ALL THE BICYCLES IN CASE OF SELECTING THE PRICE RANGE
        if (parameter == null && sortedBikesFromDB.size() == 0) {
            sortedBikesFromDB = bikeRepository.findAll();;
        }

        bikes = sortedBikesFromDB;

        // MIN & MAX - PRICE RANGE
        if (min != null && max != null) {
            // CHECKS THE PRICE OF THE BICYCLE AND ADDS IT TO THE ARRAY WHICH CONSISTS OF ALL BICYCLES THAT PASS THE MIN-MAX TEST
            int priceTest;
            for (int i = 0; i < bikes.size(); i++) {
                priceTest = bikes.get(i).getPrice();
                if (priceTest < min || priceTest > max) {
                    bikes.remove(bikes.get(i));
                }
            }
        }

        // IN CASE SOMETHING WENT WRONG WITH DATABASE CONNECTION OR THERE IS NO BIKE ENTITIES IN THE DATABASE
        if (bikes.isEmpty()) {
            throw new ResourceNotFoundException("There are no bikes.");
        }

        // USE THIS TO CHECK THE INPUTTED PAGE AND SIZE
        // EXAMPLE: We have 8 objects in our database. We want to see 6 objects per page. That means, there are 2 pages.
        // The 1. page has 6 objects. The 2. page has 2 objects. There should not be page number greater then 2 then.
        int number = bikes.size() / size;

        // SORTING METHOD WHICH SORTS BY TYPE, STATE, BRAND, FRAME SIZE OR PRICE
        switch (filter) {
            case "bikeId":
                if (sort.equals("asc")) {
                    Collections.sort(bikes, Bike::compareToById);
                } else if (sort.equals("desc")) {
                    Collections.sort(bikes, Bike::compareToById);
                    Collections.reverse(bikes);
                }
                break;

            case "type":
                if (sort.equals("desc")) {
                    Collections.sort(bikes, Bike::compareToByType);
                } else if (sort.equals("asc")) {
                    Collections.sort(bikes, Bike::compareToByType);
                    Collections.reverse(bikes);
                }
                break;

            case "state":
                if (sort.equals("desc")) {
                    Collections.sort(bikes, Bike::compareToByState);
                } else if (sort.equals("asc")) {
                    Collections.sort(bikes, Bike::compareToByState);
                    Collections.reverse(bikes);
                }
                break;

            case "brand":
                if (sort.equals("desc")) {
                    Collections.sort(bikes, Bike::compareToByBrand);
                } else if (sort.equals("asc")) {
                    Collections.sort(bikes, Bike::compareToByBrand);
                    Collections.reverse(bikes);
                }
                break;

            case "frameSize":
                if (sort.equals("desc")) {
                    Collections.sort(bikes, Bike::compareToByFrameSize);
                } else if (sort.equals("asc")) {
                    Collections.sort(bikes, Bike::compareToByFrameSize);
                    Collections.reverse(bikes);
                }
                break;

            case "price":
                if (sort.equals("asc")) {
                    Collections.sort(bikes, Bike::compareToByPrice);
                } else if (sort.equals("desc")) {
                    Collections.sort(bikes, Bike::compareToByPrice);
                    Collections.reverse(bikes);
                }
                break;
        }


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

            // MAP WHICH REPRESENTS VALUES AND IS RETURNED
            Map<String, Object> response = new HashMap<>();
            response.put("Bikes", pagedBikes);
            response.put("currentPage", page);
            response.put("totalItems", bikes.size());
            response.put("totalPages", number);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            throw new ResourceNotFoundException("Oops, something went wrong. There are no bikes that match your criteria.");
    }
}