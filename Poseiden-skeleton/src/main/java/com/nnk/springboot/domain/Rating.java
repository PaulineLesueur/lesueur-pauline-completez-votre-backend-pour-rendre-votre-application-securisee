package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "moodysRating" )
    private String moodysRating;

    @Column(name = "sandPRating" )
    private String sandPRating;

    @Column(name = "fitchRating" )
    private String fitchRating;

    @Column(name = "orderNumber" )
    private Integer order;

}
