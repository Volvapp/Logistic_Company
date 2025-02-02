package org.logisticcompany.init;


import org.logisticcompany.repository.*;
import org.logisticcompany.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final OfficeRepository officeRepository;
    private final LogisticCompanyRepository logisticCompanyRepository;


    private final RoleService roleService;
    private final UserService userService;
    private final PackageService packageService;
    private final OfficeService officeService;
    private final LogisticCompanyService logisticCompanyService;

    public AppInitializer(RoleRepository roleRepository, UserRepository userRepository, PackageRepository packageRepository,
                          OfficeRepository officeRepository, LogisticCompanyRepository logisticCompanyRepository, RoleService roleService, UserService userService, PackageService packageService, OfficeService officeService, LogisticCompanyService logisticCompanyService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
        this.officeRepository = officeRepository;
        this.logisticCompanyRepository = logisticCompanyRepository;
        this.roleService = roleService;
        this.userService = userService;
        this.packageService = packageService;
        this.officeService = officeService;
        this.logisticCompanyService = logisticCompanyService;
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
        if (this.logisticCompanyRepository.count() == 0){
            this.logisticCompanyService.initializeLogisticCompanies();
        }
        if (this.officeRepository.count() == 0){
            this.officeService.initializeOffices();
        }
    }
}