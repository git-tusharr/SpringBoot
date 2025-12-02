package com.example.controller;

import java.security.PublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.model.Student;
import com.example.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/save")
    public Student save(@RequestBody Student s) {
        return studentService.save(s);
        
    }
    
    @GetMapping("/get")
    public List<Student> get() {
  	  return studentService.getdata();
    }
    
    @DeleteMapping("/del/{id}")
    public String del(@PathVariable Integer id) {
    	return studentService.delete(id);
    }
}
