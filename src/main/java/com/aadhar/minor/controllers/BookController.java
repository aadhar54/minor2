package com.aadhar.minor.controllers;

import com.aadhar.minor.models.Book;
import com.aadhar.minor.models.BookFilterKey;
import com.aadhar.minor.requests.BookCreateRequest;
import com.aadhar.minor.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    //Admin
    @PostMapping("/book")
    public Book createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        return bookService.createBook(bookCreateRequest);
    }

    // Student + Admin
    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable(value = "id") int id){
        return bookService.getBookById(id);
    }

    @GetMapping("/hello")
    public String getHello(){
        return "Mai Hello hai !";
    }

    //     1 : Create an API to get all books of a particular genre
//
//     2 : Create an API to get all books written by a particular author
//
//     3 : Create an API to get all available books which are not yet issued to anyone .
//
//     4 : Create an API to get all books with a given book id .
//
//    TASK : Can we do something to accommodate above 4 tasks in one api ?


    // Student + Admin
    @GetMapping("/book")
    public List<Book> getBooksByKeyValue(@RequestParam("filterKey") String filterKey,
                                         @RequestParam("filterValue") String filterValue){

        switch(BookFilterKey.valueOf(filterKey)){

           case GENRE:
               return bookService.getBookByGenre(filterValue);

           case AUTHOR:
               return bookService.getBookByAuthorEmail(filterValue);

           case AVAILABILITY:
               return bookService.getBookByAvailability(Boolean.valueOf(filterValue));

           case BOOKID:
               return bookService.getBookById(filterValue);

       }

       return null;

    }

}
