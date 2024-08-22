package com.nnk.springboot.domain;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="TradeId")
    private Integer id;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(name = "buyQuantity" )
    private Double buyQuantity;

    @Column(name = "sellQuantity" )
    private Double sellQuantity;

    @Column(name = "buyPrice" )
    private Double buyPrice;

    @Column(name = "sellPrice" )
    private Double sellPrice;

    @Column(name = "tradeDate" )
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
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
