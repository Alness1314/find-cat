package com.alness.findcat.users.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alness.findcat.users.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    
}
