package org.logisticcompany.model.dto;

import org.logisticcompany.model.enums.Role;

public class RoleDto {
    private Role role;

    public RoleDto(Role role) {
        this.role = role;
    }

    public RoleDto() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role.toString();
    }
}
