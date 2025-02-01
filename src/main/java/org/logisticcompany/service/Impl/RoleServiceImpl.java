package org.logisticcompany.service.Impl;

import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.enums.Role;
import org.logisticcompany.repository.RoleRepository;
import org.logisticcompany.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initializeRoles() {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setRole(Role.ADMIN);

        RoleEntity clientRole = new RoleEntity();
        clientRole.setRole(Role.CLIENT);

        RoleEntity officeEmployeeRole = new RoleEntity();
        officeEmployeeRole.setRole(Role.OFFICE_EMPLOYEE);

        RoleEntity courierEmployeeRole = new RoleEntity();
        courierEmployeeRole.setRole(Role.COURIER_EMPLOYEE);

        roleRepository.saveAll(List.of(adminRole, clientRole, officeEmployeeRole, courierEmployeeRole));
    }
}
