package com.aadhar.minor.repositories;

import com.aadhar.minor.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    @Query(value = "select * from author where email = ?1",nativeQuery = true)
    public Author getAuthorByEmail(String authorEmail);
}
