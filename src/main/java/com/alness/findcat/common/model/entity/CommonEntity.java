package com.alness.findcat.common.model.entity;

import java.time.OffsetDateTime;
//import java.util.UUID;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name =  "create_at", nullable = false, updatable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean enabled;

}
