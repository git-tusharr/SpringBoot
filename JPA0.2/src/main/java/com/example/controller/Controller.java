package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Model;
import com.example.service.ModelService;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:5173")
public class Controller {

    @Autowired
    ModelService modelService;


    @PostMapping("/save")
    public Model save(@RequestBody Model model) {
        return modelService.save(model);
    }
}
