package com.aadhar.minor.responses;


import com.aadhar.minor.models.Book;
import com.aadhar.minor.models.MyUser;
import com.aadhar.minor.models.Request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private int id;

    private int age;

    private String rollNo;

    private String name;

    @JsonIgnoreProperties({"admin","password","student","enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
    private MyUser myUser;

    @JsonIgnoreProperties({"student","requestList"})
    private List<Book> bookList;

    private Date createdOn;

}
