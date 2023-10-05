package com.alness.findcat.profiles.dto.response;

import com.alness.findcat.common.model.dto.CommonResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse extends CommonResponse{
    private String name;
}
