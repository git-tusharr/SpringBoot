package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Model;
import com.example.repository.Repository;

@Service
public class ModelService {

    @Autowired
    Repository repository;

    // Save a new model
    public Model save(Model s) {
        return repository.save(s);
    }  

}
