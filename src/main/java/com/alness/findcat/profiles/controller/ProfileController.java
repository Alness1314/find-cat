package com.alness.findcat.profiles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.findcat.profiles.dto.request.ProfileRequest;
import com.alness.findcat.profiles.dto.response.ProfileResponse;
import com.alness.findcat.profiles.service.ProfileService;

@RestController
@RequestMapping("profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping()
    public ResponseEntity<?> findAll(){
        List<ProfileResponse> response = profileService.find();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id){
        ProfileResponse response = profileService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody ProfileRequest request){
        ProfileResponse response = profileService.save(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ProfileRequest request){
        ProfileResponse response = profileService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
