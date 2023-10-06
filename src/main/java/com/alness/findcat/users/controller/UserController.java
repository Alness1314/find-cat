package com.alness.findcat.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.findcat.users.dto.request.UserRequest;
import com.alness.findcat.users.dto.response.UserResponse;
import com.alness.findcat.users.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("users")
@Tag(name = "Users", description = "Registration of users to manage access.")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> findAll(){
        List<UserResponse> response = userService.find();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id){
        UserResponse response = userService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody UserRequest request){
        UserResponse response = userService.save(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
