package com.autoyard.project.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.math.BigDecimal;


@Entity
@Table(name = "receiving_acts")
@Getter
@Setter
@RequiredArgsConstructor
public class ReceivingAct extends AbstractDocumentEntity {

    @Column(name = "doc_number", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ra_dn_gen")
    @SequenceGenerator(name = "ra_dn_gen", sequenceName = "receiving_acts_doc_number_seq", allocationSize = 1)
    @Generated(value = GenerationTime.INSERT)
    private Integer docNumber;

    @ManyToOne
    @JoinColumn(name = "contractor_id", nullable = false)
    private Contractor contractor;

    @ManyToOne
    @JoinColumn(name = "vin_code_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "gross_weight", nullable = false)
    private BigDecimal grossWeight;

    @ManyToOne
    @JoinColumn(name = "cell_id")
    private CellOfYard cell;
}
