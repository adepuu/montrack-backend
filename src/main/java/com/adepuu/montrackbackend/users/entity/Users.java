package com.adepuu.montrackbackend.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "montrack")
public class Users {
  @Id
  @ColumnDefault("nextval('montrack.users_id_seq'::regclass)")
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
  private String username;

  @Size(max = 150)
  @NotNull
  @Column(name = "email", nullable = false, length = 150)
  private String email;

  @Size(max = 255)
  @Column(name = "display_name")
  private String displayName;

  @Size(max = 255)
  @Column(name = "avatar")
  private String avatar;

  @Size(max = 150)
  @Column(name = "quotes", length = 150)
  private String quotes;

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

  @PrePersist
  public void prePersist() {
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = Instant.now();
  }

  @PreRemove
  public void preRemove() {
    this.deletedAt = Instant.now();
  }
}