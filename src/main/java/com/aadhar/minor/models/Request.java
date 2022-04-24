package com.aadhar.minor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String externalRequestId;

    @Enumerated(value = EnumType.STRING)
    private RequestType requestType;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("requestList")
    private Student student;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("requestList")
    private Book book;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("requestsToProcess")
    private Admin admin;

    @OneToOne(mappedBy = "request")
    @JsonIgnoreProperties("request")
    private Transaction transaction;

    private String adminComment;

    @CreationTimestamp
    private Date createdOn;

}
