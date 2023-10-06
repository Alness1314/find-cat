package com.alness.findcat.users.dto.response;

import java.util.List;

import com.alness.findcat.common.model.dto.CommonResponse;
import com.alness.findcat.profiles.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends CommonResponse{
    private String username;
    private String password;
    private String detail;
    private List<Profile> profiles;
}
