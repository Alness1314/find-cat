package com.alness.findcat.profiles.service.impl;

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

import com.alness.findcat.profiles.dto.request.ProfileRequest;
import com.alness.findcat.profiles.dto.response.ProfileResponse;
import com.alness.findcat.profiles.entity.Profile;
import com.alness.findcat.profiles.repository.ProfileRepository;
import com.alness.findcat.profiles.service.ProfileService;
import com.alness.findcat.utils.Validations;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private ProfileRepository profileRepo;

    ModelMapper mapper = new ModelMapper();

    @Override
    public ProfileResponse findOne(String id) {
        Profile profile = internalFindOne(id);
        if (profile == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile with id: [%s] not found", id));
        }
        return getProfileMapping(profile);
    }

    @Override
    public ProfileResponse findOneInt(String id) {
        Profile profile = internalFindOne(id);
        if (profile == null){
            return null;
        }
        return getProfileMapping(profile);
    }

    @Override
    public List<ProfileResponse> find() {
        List<Profile> profilesEntities = profileRepo.findAll();
        List<ProfileResponse> profiles = new ArrayList<>();
        profilesEntities.stream().forEach( profile -> {
            profiles.add(getProfileMapping(profile));
        });

        return profiles;
    }

    @Override
    public ProfileResponse save(ProfileRequest request) {
        Profile newProfile = mapper.map(request, Profile.class);
        try {
            newProfile.setCreateAt(OffsetDateTime.now());
            newProfile.setEnabled(true);
            newProfile = profileRepo.save(newProfile);    
        } catch (Exception e) {
            log.error("error to save profile: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("error to save profile: [%s]", e.getMessage()));
        }
        return getProfileMapping(newProfile);
    }

    @Override
    public ProfileResponse update(String id, ProfileRequest request) {
        Profile profileUpdate = internalFindOne(id);
        mapper.map(profileUpdate, request);
        try {
            profileUpdate = profileRepo.save(profileUpdate);    
        } catch (Exception e) {
          log.error("error to update profile: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("error to update profile: [%s]", e.getMessage()));
        }

        return getProfileMapping(profileUpdate);
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private Profile internalFindOne(String id){
        Optional<Profile> profile = null;
        if(Validations.isUUID(id)){
            profile = profileRepo.findById(UUID.fromString(id));
        }else{
            profile = profileRepo.findByName(id);
        }
        if(!profile.isPresent()){
            return null;
        }
        return profile.get();
    }

    private ProfileResponse getProfileMapping(Profile source){
        return mapper.map(source, ProfileResponse.class);
    }

    
    
}
