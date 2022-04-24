package com.aadhar.minor.models;

import com.aadhar.minor.responses.StudentResponse;
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
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int age;

    @Column(unique = true,nullable = false)
    private String rollNo;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"admin","password","student","enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
    private MyUser myUser;

    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("student")
    private List<Book> bookList;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private List<Request> requestList;

    @CreationTimestamp
    private Date createdOn;

    public StudentResponse toStudentResponse(){
        return StudentResponse.builder()
                .id(getId())
                .age(getAge())
                .rollNo(getRollNo())
                .name(getName())
                .myUser(getMyUser())
                .bookList(getBookList())
                .createdOn(getCreatedOn())
                .build();
    }
}
