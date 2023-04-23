package com.autoyard.project.domain.entity;

import com.autoyard.project.domain.enumeration.RecordType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicle_turnovers")
@Setter
@Getter
public class VehicleTurnovers {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "id", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_type", nullable = false)
    private RecordType recordType;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "period", columnDefinition = "timestamptz", nullable = false)
    private LocalDateTime period;

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
