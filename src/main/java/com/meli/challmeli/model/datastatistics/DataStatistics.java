package com.meli.challmeli.model.datastatistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataStatistics {
    @Id
    @Column
    private int id;
    @Column
    private Double average;
    @Column
    private Double min;
    @Column
    private Double max;
    @Column
    private Integer cantInvocations;
}
