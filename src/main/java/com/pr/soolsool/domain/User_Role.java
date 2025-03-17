package com.pr.soolsool.domain;

import lombok.Getter;

@Getter
public enum User_Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    User_Role(String value) {
        this.value = value;
    }

    private String value;
}
