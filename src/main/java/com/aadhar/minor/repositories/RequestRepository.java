package com.aadhar.minor.repositories;

import com.aadhar.minor.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
