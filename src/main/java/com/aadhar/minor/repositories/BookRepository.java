package com.aadhar.minor.repositories;

import com.aadhar.minor.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query("select b from Book b where b.genre = :genre")
    List<Book> findByGenre(String genre);

    @Query(value = "select * from book where student_id IS NULL",nativeQuery = true)
    List<Book> findByAvailableTrue();

    @Query(value = "select * from book where student_id IS NOT NULL",nativeQuery = true)
    List<Book> findByIssuedStatus();

    @Query(value = "select * from book b join author a on b.author_id=a.id where a.email=?1",nativeQuery = true)
    List<Book> findByAuthorEmail(String filterValue);
}
