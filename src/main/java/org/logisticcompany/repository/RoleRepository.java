package org.logisticcompany.repository;

import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByRole(Role roleEnum);

}
