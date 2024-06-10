package com.adepuu.montrackbackend.trx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pocket", schema = "montrack")
public class Pocket {
    @Id
    @ColumnDefault("nextval('montrack.pocket_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Size(max = 20)
    @NotNull
    @Column(name = "emoji", nullable = false, length = 20)
    private String emoji;

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "budget", nullable = false, precision = 10, scale = 5)
    private BigDecimal budget;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "spent", nullable = false, precision = 10, scale = 5)
    private BigDecimal spent;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

}