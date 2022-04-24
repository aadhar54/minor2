package com.aadhar.minor.services;

import com.aadhar.minor.models.Author;
import com.aadhar.minor.models.Book;
import com.aadhar.minor.repositories.AuthorRepository;
import com.aadhar.minor.repositories.BookRepository;
import com.aadhar.minor.requests.BookCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(BookCreateRequest bookCreateRequest){

        Author tempAuthor = bookCreateRequest.toAuthor();
        //check if author already exists through some other book in Author table
        Author author = authorService.createOrGetAuthor(tempAuthor);

        Book book = bookCreateRequest.toBook();
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public Book saveOrUpdateBook(Book book){
        return bookRepository.save(book);
    }


    public Book getBookById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getBookById(String id){
        return Collections.singletonList(bookRepository.findById(Integer.parseInt(id)).orElse(null));
    }


    public List<Book> getBookByGenre(String filterValue) {
        return bookRepository.findByGenre(filterValue);
    }

    public List<Book> getBookByAuthorEmail(String filterValue) {
        return bookRepository.findByAuthorEmail(filterValue);
    }

    public List<Book> getBookByAvailability(Boolean valueOf) {
        if(valueOf){
            return bookRepository.findByAvailableTrue();
        }
        return bookRepository.findByIssuedStatus();
    }
}
