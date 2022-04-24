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
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"admin","password","student","enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
    private MyUser myUser;


    @JsonIgnoreProperties({"admin", "student", "book", "transaction"})
    @OneToMany(mappedBy = "admin")
    private List<Request> requestsToProcess;

    @CreationTimestamp
    private Date createdOn;
}
