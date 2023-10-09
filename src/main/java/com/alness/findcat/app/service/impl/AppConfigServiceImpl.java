package com.alness.findcat.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.findcat.app.dto.ResponseDto;
import com.alness.findcat.app.service.AppConfigService;
import com.alness.findcat.common.enums.AllowedProfiles;
import com.alness.findcat.profiles.dto.request.ProfileRequest;
import com.alness.findcat.profiles.dto.response.ProfileResponse;
import com.alness.findcat.profiles.service.ProfileService;
import com.alness.findcat.users.dto.request.UserRequest;
import com.alness.findcat.users.dto.response.UserResponse;
import com.alness.findcat.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppConfigServiceImpl implements AppConfigService {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseDto createDefaultValues() {
        boolean resultAdmin = createProfile(AllowedProfiles.ADMIN.getName());
        boolean resultEmployee = createProfile(AllowedProfiles.EMPLOYEE.getName());
        boolean resultUser = createProfile(AllowedProfiles.USER.getName());
        String profileId = findProfileId(AllowedProfiles.ADMIN.getName());
        boolean resultDefaultUser = createUser(new UserRequest("admin@gmail.com", "9R9NTz7<s?", null, profileId));

        if (resultAdmin && resultEmployee && resultUser && resultDefaultUser) {
            return new ResponseDto("All profiles were created successfully.", HttpStatus.OK, true);
        } else {
            return new ResponseDto("Failed to create profiles, some profiles were not created.", HttpStatus.BAD_REQUEST,
                    false);
        }
    }

    private boolean saveProfile(String profileName) {
        ProfileResponse profile = profileService.save(new ProfileRequest(profileName));
        return profile != null;
    }

    private boolean findProfile(String profileName){
        ProfileResponse profile = profileService.findOneInt(profileName);
        return profile != null;
    }

    private String findProfileId(String profileName){
        ProfileResponse profile = profileService.findOneInt(profileName);
        if(profile != null){
            return profile.getId();
        }
        return null;
    }

    private Boolean createProfile(String profileName){
        if(findProfile(profileName)){
            log.info(String.format("The profile with the name: [%s] already exists.", profileName));
            return false;
        }else{
            log.info(String.format("The profile with the name: [%s] created successfully.", profileName));
            return saveProfile(profileName);
        }
    }

    private boolean saveUser(UserRequest userDefautl){
        UserResponse user = userService.save(userDefautl);
        return user != null;
    }

    private boolean findUser(String username){
        UserResponse user = userService.findOneInt(username);
        return user != null;
    }

    private Boolean createUser(UserRequest userDefault){
        if(findUser(userDefault.getUsername())){
            log.info(String.format("The user with the username: [%s] already exists.", userDefault.getUsername()));
            return false;
        }else{
            log.info(String.format("The user with the username: [%s] created successfully.", userDefault.getUsername()));
            return saveUser(userDefault);
        }
    }

}
