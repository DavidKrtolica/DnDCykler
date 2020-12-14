package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ControllerHTML {

    @Autowired
    BikeRepository bikeRepository;

    @GetMapping("/view")
    public String index(Model model) {
        List<Bike> bikes = bikeRepository.findAll();
        model.addAttribute("bikes", bikes);
        return "/index";
    }

}
