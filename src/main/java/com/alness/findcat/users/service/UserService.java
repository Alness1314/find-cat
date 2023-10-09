package com.alness.findcat.users.service;

import java.util.List;

import com.alness.findcat.users.dto.request.UserRequest;
import com.alness.findcat.users.dto.response.UserResponse;

public interface UserService {
    public List<UserResponse> find();
    public UserResponse findOne(String id);
    public UserResponse findOneInt(String id);
    public UserResponse save(UserRequest request);
    public UserResponse update(String id, UserRequest request);
    public void delete(String id);
}
