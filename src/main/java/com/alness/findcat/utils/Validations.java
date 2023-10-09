package com.alness.findcat.utils;

import java.util.UUID;

public class Validations {
    
    public static Boolean isUUID(String uuid){
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
