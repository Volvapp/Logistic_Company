package org.logisticcompany.init;


import org.logisticcompany.repository.RoleRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.RoleService;
import org.logisticcompany.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    private final RoleService roleService;
    private final UserService userService;

    public AppInitializer(RoleRepository roleRepository, UserRepository userRepository, RoleService roleService, UserService userService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        if(roleRepository.count() == 0){
            roleService.initializeRoles();
        }
        if(userRepository.count() == 0){
            userService.initializeUsers();
        }
    }
}