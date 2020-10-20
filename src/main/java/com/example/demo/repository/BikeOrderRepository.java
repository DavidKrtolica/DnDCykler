package com.example.demo.repository;

import com.example.demo.model.BikeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeOrderRepository extends JpaRepository<BikeOrder, Integer> {
}
