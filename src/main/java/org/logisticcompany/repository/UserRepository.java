package org.logisticcompany.repository;

import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.role IN (:roles)")
    List<UserEntity> findUserEntitiesByRoles(Set<Role> roles);
}
