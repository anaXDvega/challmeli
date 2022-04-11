package com.meli.challmeli.model.distance;
import lombok.*;

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
@Builder
public class Distance {
    @Id
    @Column
    private Integer geonameId;
    @Column
    private String country;
    @Column
    private String regionName;
    @Column
    private Double distance;
    @Column
    private Integer invocations;
}
