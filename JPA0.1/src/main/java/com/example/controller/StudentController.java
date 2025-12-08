package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Student;
import com.example.service.StudentService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/pagi")
    public List<Student> pagi(@RequestParam int pg, @RequestParam int size) {
        return studentService.pagi(pg, size);
    }
    
    @PostMapping("/sort")
    public List<Student> sortByField(
            @RequestParam String field,
            @RequestParam String direction) {
        return studentService.sortbyfield(field, direction);
    }
    
    @PostMapping("/filter")
    public List<Student> name(@RequestParam String cls) {
    	return studentService.filter(cls);
    }
}
