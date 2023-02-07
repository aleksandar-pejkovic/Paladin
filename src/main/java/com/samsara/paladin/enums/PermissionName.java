package com.samsara.paladin.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionName implements GrantedAuthority {

    READ,
    WRITE,
    UPDATE,
    DELETE;

    @Override
    public String getAuthority() {
        return "SCOPE_" + name();
    }
}
