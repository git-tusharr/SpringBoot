package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.model.Student;
import com.example.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student save(Student s) {
        return studentRepository.save(s);
    }

    public List<Student> getdata() {
        return studentRepository.findAll();
    }

    public String delete(Integer id) {
        studentRepository.deleteById(id);
        return "deleted successfully";
    }

    public List<Student> pagi(int pg, int size) {
        Page<Student> page = studentRepository.findAll(PageRequest.of(pg, size));
        return page.getContent();
    }
    
    
    public List<Student> sortbyfield(String field,String direction) {
    	
    	Sort sort=direction.equals("asc")?
    			Sort.by(field).ascending():Sort.by(field).descending();
    	return studentRepository.findAll(sort);
		
	}
}
