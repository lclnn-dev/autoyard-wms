package com.autoyard.project.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractDocumentEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "id", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "date_time", columnDefinition = "timestamptz", nullable = false)
    private LocalDateTime docDateTime = LocalDateTime.now();
}
