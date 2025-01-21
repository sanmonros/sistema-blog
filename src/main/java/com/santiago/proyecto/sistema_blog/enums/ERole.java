package com.santiago.proyecto.sistema_blog.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ERole {
    ROLE_USER,
    ROLE_ADMIN;


    @JsonCreator
    public static ERole fromString(String role) {
        return ERole.valueOf(role.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
