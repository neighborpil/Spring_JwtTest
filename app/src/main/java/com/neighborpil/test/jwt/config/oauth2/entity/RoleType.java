package com.neighborpil.test.jwt.config.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    USER("ROLE_USER", "User role"),
    ADMIN("ROLE_ADMIN", "Administrator role"),
    GUEST("ROLE_GUEST", "Guest role");

    private final String code;
    private final String displayName;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(roleType -> roleType.getCode().equals(code))
                .findAny()
                .orElse(GUEST);
    }
}
