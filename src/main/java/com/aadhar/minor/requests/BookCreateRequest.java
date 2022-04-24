package com.aadhar.minor.requests;

import com.aadhar.minor.models.Author;
import com.aadhar.minor.models.Book;
import com.aadhar.minor.models.Genre;
import com.aadhar.minor.models.Student;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookCreateRequest {

    @NotNull
    private String bookTitle;

    @NotNull
    private String bookGenre;


    //passing author properties to create author before persisting a book
    @NotNull
    private String authorName;

    @NotNull
    @Email
    private String authorEmail;

    private String authorWebsite;

    public Book toBook(){
        return Book.builder()
                .title(this.getBookTitle())
                .genre(Genre.valueOf(this.getBookGenre()))
                .build();
    }

    public Author toAuthor(){
        return Author.builder()
                .name(this.getAuthorName())
                .email(this.getAuthorEmail())
                .website(this.getAuthorWebsite())
                .build();
    }

}
