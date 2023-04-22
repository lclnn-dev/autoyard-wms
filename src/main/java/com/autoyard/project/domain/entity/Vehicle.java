package com.autoyard.project.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends AbstractCatalogEntity {

    @Column(name = "custom_code", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_code_gen")
    @SequenceGenerator(name = "vehicle_code_gen", sequenceName = "vehicles_code_seq", allocationSize = 1)
    @Generated(value = GenerationTime.INSERT)
    private Integer customCode;

    @Column(name = "vin_code", length = 17, nullable = false)
    private String vinCode;

    @Column(name = "make", length = 25, nullable = false)
    private String make;

    @Column(name = "model", length = 25, nullable = false)
    private String model;

    @Column(name = "year", length = 4, nullable = false)
    private String year;
}
