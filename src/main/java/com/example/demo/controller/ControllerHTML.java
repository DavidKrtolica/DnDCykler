package com.example.demo.controller;

import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ControllerHTML {

    @Autowired
    BikeRepository bikeRepository;

    @RequestMapping(value = {"", "/", "/index", "/index/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        List<Bike> bikes = bikeRepository.findAll();
        model.addAttribute("bikes", bikes);
        return "index";
    }

}
