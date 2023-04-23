package com.autoyard.project.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cells_yard")
@Getter
@Setter
public class CellOfYard extends AbstractCatalogEntity {

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "folder", nullable = false)
    private boolean isFolder;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private CellOfYard parent;
}
