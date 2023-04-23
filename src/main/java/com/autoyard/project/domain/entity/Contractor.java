package com.autoyard.project.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "contractors")
@Getter
@Setter
public class Contractor extends AbstractCatalogEntity {

    @Column(name = "custom_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractor_code_gen")
    @SequenceGenerator(name = "contractor_code_gen", sequenceName = "contractors_code_seq", allocationSize = 1)
    @Generated(value = GenerationTime.INSERT)
    private Integer customCode;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "full_name", length = 300, nullable = false)
    private String fullName;

    @Column(name = "edrpou", length = 10, nullable = false)
    private String edrpou;
}

