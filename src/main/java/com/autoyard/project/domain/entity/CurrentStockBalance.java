package com.autoyard.project.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_balances")
@Setter
@Getter
@ToString
public class CurrentStockBalance {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "id", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "date_arrival", columnDefinition = "timestamptz", nullable = false)
    private LocalDateTime dateArrival;

    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "cell_id", nullable = false)
    private CellOfYard cell;

    @Column(name = "recorder_id", nullable = false)
    private UUID recorderId;

    @Column(name = "quantity")
    private int quantity;
}
