package com.example.demo;

import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTests {

    @Autowired
    BikeRepository bikeRepo;

    @BeforeEach
    public void init(){
        bikeRepo.deleteAll();
    }

    @Test
    public void should_find_no_bikes(){
        Iterable<Bike> bikes = bikeRepo.findAll();
        assertThat(bikes).isEmpty();
    }

    @Test
    public void should_find_by_brand_containing(){
        bikeRepo.save(new Bike("Race", "New", "GT", "XL", 6000));
        bikeRepo.save(new Bike("Mountain", "Old", "Trek", "L", 2500));
        bikeRepo.save(new Bike("City", "Used", "GT", "M", 3000));
        Iterable<Bike> bikesByBrand = bikeRepo.findByBrandContaining("gt");
        assertThat(bikesByBrand).hasSize(2);
    }

    @Test
    public void should_find_by_type_containing(){
        bikeRepo.save(new Bike("Race", "New", "GT", "XL", 6000));
        bikeRepo.save(new Bike("Mountain", "Old", "Trek", "L", 2500));
        bikeRepo.save(new Bike("Race", "Used", "GT", "M", 3000));
        Iterable<Bike> bikesByType = bikeRepo.findByTypeContaining("race");
        assertThat(bikesByType).hasSize(2);
    }

    @Test
    public void should_find_by_state_containing(){
        bikeRepo.save(new Bike("Race", "New", "GT", "XL", 6000));
        bikeRepo.save(new Bike("Mountain", "Old", "Trek", "L", 2500));
        bikeRepo.save(new Bike("Race", "New", "GT", "M", 3000));
        Iterable<Bike> bikesByType = bikeRepo.findByStateContaining("new");
        assertThat(bikesByType).hasSize(2);
    }

    @Test
    public void should_find_by_frame_size_containing(){
        bikeRepo.save(new Bike("Race", "New", "GT", "XL", 6000));
        bikeRepo.save(new Bike("Mountain", "Old", "Trek", "L", 2500));
        bikeRepo.save(new Bike("Race", "Used", "GT", "XL", 3000));
        Iterable<Bike> bikesByType = bikeRepo.findByFrameSizeContaining("xl");
        assertThat(bikesByType).hasSize(2);
    }

}