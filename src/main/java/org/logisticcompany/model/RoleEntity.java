package org.logisticcompany.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.logisticcompany.model.enums.Role;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_entities")
public class RoleEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
