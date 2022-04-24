package com.aadhar.minor.services;

import com.aadhar.minor.models.Author;
import com.aadhar.minor.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    Author createOrGetAuthor(Author tempAuthor){
        Author retrievedAuthor = authorRepository.getAuthorByEmail(tempAuthor.getEmail());
        if(retrievedAuthor!=null){
            return retrievedAuthor;
        }

        return authorRepository.save(tempAuthor);
    }

}
