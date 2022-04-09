package com.meli.challmeli.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
public class Distance {
    @Id
    @Column
    private int codCountry;
    @Column
    private String country;
    @Column
    private Double distance;
    @Column
    private Integer invocations;
}
