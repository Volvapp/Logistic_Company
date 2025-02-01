package org.logisticcompany.model;

import org.logisticcompany.model.enums.Role;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "RoleEntity{" +
                "role=" + role +
                '}';
    }
}
