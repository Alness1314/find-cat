package com.alness.findcat.users.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alness.findcat.profiles.entity.Profile;
import com.alness.findcat.profiles.repository.ProfileRepository;
import com.alness.findcat.users.dto.request.UserRequest;
import com.alness.findcat.users.dto.response.UserResponse;
import com.alness.findcat.users.entity.UserEntity;
import com.alness.findcat.users.repository.UserRepository;
import com.alness.findcat.users.service.UserService;
import com.alness.findcat.utils.Validations;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    ModelMapper mapper = new ModelMapper();

    @PostConstruct
    public void init(){
        mapper.getConfiguration().setSkipNullEnabled(true);
    }

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
        UserResponse user = findOneInternal(id);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with id: [%s] not found.", id));
        }
        return user;
    }

    @Override
    public UserResponse findOneInt(String id) {
        UserResponse user = findOneInternal(id);
        if(user == null){
            return null;
        }
        return user;
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
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
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
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    private UserResponse getUser(UserEntity source){
        return mapper.map(source, UserResponse.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loading user name: {}", username);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            log.error(String.format("User with name: [%s] not found in database", username));
            throw new UsernameNotFoundException(String.format("User with name: [%s] not found in database", username));
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        user.get().getProfiles().forEach(profile -> {
            authorities.add(new SimpleGrantedAuthority(profile.getName()));
        });
        return new User(user.get().getUsername(), user.get().getPassword(), authorities);
    }

    private UserResponse findOneInternal(String id){
        Optional<UserEntity> user = null;
        if(Validations.isUUID(id)){
            user = userRepository.findById(UUID.fromString(id));
        }else{
            user = userRepository.findByUsername(id);
        }
        if(!user.isPresent()){
            return null;
        }
        return getUser(user.get());
    }
    
}
