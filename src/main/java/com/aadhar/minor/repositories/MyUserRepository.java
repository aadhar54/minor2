package com.aadhar.minor.repositories;

import com.aadhar.minor.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser,Integer> {

    MyUser findByUsername(String username);
}
