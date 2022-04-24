package com.aadhar.minor.repositories;

import com.aadhar.minor.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
