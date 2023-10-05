package com.alness.findcat.profiles.entity;

import java.io.Serializable;

import com.alness.findcat.common.model.entity.CommonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile extends CommonEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true, columnDefinition = "character varying(64)")
    private String name;
}
