package org.logisticcompany.model;

import jakarta.persistence.*;
import org.logisticcompany.model.enums.Role;

@Entity

@Table(name = "role_entities")
public class RoleEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
