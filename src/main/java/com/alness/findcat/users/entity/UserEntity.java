package com.alness.findcat.users.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alness.findcat.common.model.entity.CommonEntity;
import com.alness.findcat.profiles.entity.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true, columnDefinition = "character varying(64)")
    private String username;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String password;

    @Column(name = "detail", columnDefinition = "character varying(255)")
    private String detail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_profile", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "user_id", "profile_id" }) })
    private List<Profile> profiles = new ArrayList<>();
}
