package com.alness.findcat.profiles.service;

import java.util.List;

import com.alness.findcat.profiles.dto.request.ProfileRequest;
import com.alness.findcat.profiles.dto.response.ProfileResponse;

public interface ProfileService {
    public ProfileResponse findOne(String id);
    public List<ProfileResponse> find();
    public ProfileResponse save(ProfileRequest request);
    public ProfileResponse update(String id, ProfileRequest request);
    public void delete(String id);
}
