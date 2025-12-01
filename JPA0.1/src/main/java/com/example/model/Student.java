package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    private Integer id;
    private String name;
    private String cls;
    private String area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
