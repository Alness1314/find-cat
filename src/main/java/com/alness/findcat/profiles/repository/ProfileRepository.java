package com.alness.findcat.profiles.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alness.findcat.profiles.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, UUID>{
    
}
