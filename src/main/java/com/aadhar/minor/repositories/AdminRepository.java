package com.aadhar.minor.repositories;

import com.aadhar.minor.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
