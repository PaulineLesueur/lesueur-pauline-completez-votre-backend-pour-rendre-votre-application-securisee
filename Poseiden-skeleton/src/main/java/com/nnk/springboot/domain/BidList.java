package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BidListId")
    private Integer id;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @Min(value = 0, message = "Must be positive")
    @Column(name = "bidQuantity" )
    private Double bidQuantity;

    @Min(value = 0, message = "Must be positive")
    @Column(name = "askQuantity" )
    private Double askQuantity;

    @Min(value = 0, message = "Must be positive")
    private Double bid;

    @Min(value = 0, message = "Must be positive")
    private Double ask;
    private String benchmark;

    @Column(name = "bidListDate" )
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;

    @Column(name = "creationName" )
    private String creationName;

    @Column(name = "creationDate" )
    private Timestamp creationDate;

    @Column(name = "revisionName" )
    private String revisionName;

    @Column(name = "revisionDate" )
    private Timestamp revisionDate;

    @Column(name = "dealName" )
    private String dealName;

    @Column(name = "dealType" )
    private String dealType;

    @Column(name = "sourceListId" )
    private String sourceListId;
    private String side;

}
