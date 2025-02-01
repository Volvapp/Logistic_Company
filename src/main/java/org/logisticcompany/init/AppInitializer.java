package org.logisticcompany.init;


import org.logisticcompany.repository.PackageRepository;
import org.logisticcompany.repository.RoleRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.PackageService;
import org.logisticcompany.service.RoleService;
import org.logisticcompany.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;


    private final RoleService roleService;
    private final UserService userService;
    private final PackageService packageService;

    public AppInitializer(RoleRepository roleRepository, UserRepository userRepository, PackageRepository packageRepository,
                          RoleService roleService, UserService userService, PackageService packageService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
        this.roleService = roleService;
        this.userService = userService;
        this.packageService = packageService;
    }

    @Override
    public void run(String... args) {

        if(roleRepository.count() == 0){
            roleService.initializeRoles();
        }
        if(userRepository.count() == 0){
            userService.initializeUsers();
        }
        if (this.packageRepository.count() == 0){
            this.packageService.initializePackages();
        }
    }
}