package com.alness.findcat.users.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alness.findcat.profiles.entity.Profile;
import com.alness.findcat.profiles.repository.ProfileRepository;
import com.alness.findcat.users.dto.request.UserRequest;
import com.alness.findcat.users.dto.response.UserResponse;
import com.alness.findcat.users.entity.UserEntity;
import com.alness.findcat.users.repository.UserRepository;
import com.alness.findcat.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public List<UserResponse> find() {
        List<UserEntity> users = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        users.stream().forEach( user -> {
            result.add(getUser(user));
        });
        return result;
    }

    @Override
    public UserResponse findOne(String id) {
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(id));
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with id: [%s] not found.", id));
        }
        return getUser(user.get());
    }

    @Override
    public UserResponse save(UserRequest request) {
        UserEntity newUser = mapper.map(request, UserEntity.class);
        newUser.setCreateAt(OffsetDateTime.now());
        //set roles
        if(request.getProfiles().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("profile with id: [%s] not valid.", request.getProfiles()));
        }
        Optional<Profile> profile = profileRepository.findById(UUID.fromString(request.getProfiles()));
        if(!profile.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("profile with id: [%s] not found, contact to admin", request.getProfiles()));
        }
        List<Profile> profiles = new ArrayList<Profile>();
        profiles.add(profile.get());
        newUser.setProfiles(profiles);
        //newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            newUser.setCreateAt(OffsetDateTime.now());
            newUser.setEnabled(true);
            newUser = userRepository.save(newUser);
        } catch (Exception e) {
            log.info("Error saving user: {}", e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("Problem with saving user: [%s]", e.getMessage()));
        }
        return getUser(newUser);
    }

    @Override
    public UserResponse update(String id, UserRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    private UserResponse getUser(UserEntity source){
        return mapper.map(source, UserResponse.class);
    }
    
}
