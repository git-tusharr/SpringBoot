package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Student;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findByCls(String cls);

}
