package com.alness.findcat.common.model.dto;

import java.time.OffsetDateTime;
//import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class CommonResponse {
    private String id;
    private OffsetDateTime createAt;
    private Boolean enabled;
}
