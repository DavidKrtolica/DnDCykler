package com.example.demo.repository;

import com.example.demo.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Integer> {
    List<Bike> findByBrandContaining(String brand);
    List<Bike> findByTypeContaining(String type);
    List<Bike> findByStateContaining(String state);
    List<Bike> findByFrameSizeContaining(String frameSize);
}
