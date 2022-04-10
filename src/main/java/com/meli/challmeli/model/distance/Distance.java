package com.meli.challmeli.model.distance;
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
    private Integer geonameId;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private Double distance;
    @Column
    private Integer invocations;
}
