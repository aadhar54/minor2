package com.aadhar.minor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;
    
    private String website;

    @JsonIgnoreProperties("author")
    @OneToMany(mappedBy = "author")
    private List<Book> bookList;

    @CreationTimestamp
    private Date createdOn;
}
