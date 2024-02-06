package com.example.demo.common.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class CommonEntity implements Serializable {

    @Column(name = "register_id", length = 45, columnDefinition = "VARCHAR(45)")
    private String registerId;

    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME")
    private ZonedDateTime deletedAt;

    @PrePersist
    public void onPersist() {
        if (this.createdAt == null) {
            this.createdAt = ZonedDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {
        if (this.createdAt == null) {
            this.createdAt = ZonedDateTime.now();
        }
        this.updatedAt = ZonedDateTime.now();
    }
}