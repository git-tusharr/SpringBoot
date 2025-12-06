package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Model;
import com.example.model.Student;
import com.example.service.ModelService;
import com.example.service.Service;
import com.example.service.StudentService;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:5174")
public class Controller {

    @Autowired
    ModelService modelService;

    @PostMapping("/save")
    public Model save(@RequestBody Model s) {
        return modelService.save(s);
    }
    
   
    }
}
