package com.aadhar.minor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String externalTransactionId;

    @JsonIgnoreProperties("transaction")
    @OneToOne
    @JoinColumn
    private Request request;


    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    private Double fine;

    @CreationTimestamp
    private Date createdOn;
}
