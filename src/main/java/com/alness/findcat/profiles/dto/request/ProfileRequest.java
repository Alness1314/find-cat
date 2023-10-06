package com.alness.findcat.profiles.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    @Pattern(regexp = "^(Administrator|User|Employee)$", message = "Enter a valid profile name.")
    private String name;
}
