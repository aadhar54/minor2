package com.aadhar.minor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")
    private Student student;

    @JsonIgnoreProperties("book")
    @OneToMany(mappedBy = "book")
    private List<Request> requestList;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")
    private Author author;

    @CreationTimestamp
    private Date createdOn;

}
