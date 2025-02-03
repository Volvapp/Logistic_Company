package org.logisticcompany.service.Impl;

import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.UserDto;
import org.logisticcompany.model.enums.PackagePaidStatus;
import org.logisticcompany.model.enums.Role;
import org.logisticcompany.model.enums.State;
import org.logisticcompany.model.service.EmployeeServiceModel;
import org.logisticcompany.model.service.UserServiceModel;
import org.logisticcompany.model.view.UserViewModel;
import org.logisticcompany.repository.LogisticCompanyRepository;
import org.logisticcompany.repository.PackageRepository;
import org.logisticcompany.model.view.UserBalanceViewModel;
import org.logisticcompany.repository.RoleRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.UserService;
import org.logisticcompany.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PackageRepository packageRepository;

    private final ModelMapper modelMapper;

    private final LogisticCompanyUserServiceImpl logisticCompanyUserService;

    private final PasswordEncoder passwordEncoder;

    private final LogisticCompanyRepository logisticCompanyRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PackageRepository packageRepository,
                           ModelMapper modelMapper, LogisticCompanyUserServiceImpl logisticCompanyUserService, PasswordEncoder passwordEncoder, LogisticCompanyRepository logisticCompanyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.packageRepository = packageRepository;
        this.modelMapper = modelMapper;
        this.logisticCompanyUserService = logisticCompanyUserService;
        this.passwordEncoder = passwordEncoder;
        this.logisticCompanyRepository = logisticCompanyRepository;
    }

    @Override
    public UserEntity createUser(UserDto userDto) {
        // Map DTO to entity
        UserEntity user = this.modelMapper.map(userDto, UserEntity.class);

        // Save the user to the repository
        this.userRepository.save(user);
        log.info(String.format("User: %s created", user.getUsername()));

        return user;
    }

    @Override
    public List<UserDto> getUsers() {
        // Retrieve all users from the repository
        List<UserEntity> users = this.userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        // Convert UserEntity list to UserDto list
        for (UserEntity user : users) {
            userDtos.add(this.modelMapper.map(user, UserDto.class));
        }

        return userDtos;
    }

    @Override
    public UserEntity updateUser(UserDto userDto, Long id) {
        // Find user by ID, throw exception if not found
        UserEntity user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id: %d not found", id)));

        // Update user fields with new data
        this.modelMapper.map(userDto, user);

        // Save updated user
        this.userRepository.save(user);
        log.info(String.format("User: %s updated", user.getUsername()));

        return user;
    }

    @Override
    public void deleteUser(Long id) {
        // Delete user by ID
        this.userRepository.deleteById(id);
        log.info(String.format("User with id: %s deleted", id));
    }

    @Override
    public String getAllEmployees() {
        List<UserDto> userDtos = new ArrayList<>();

        // Fetch all employees based on their roles
        List<UserEntity> userEntitiesByRoles = this.userRepository
                .findUserEntitiesByRoles(Set.of(Role.OFFICE_EMPLOYEE, Role.COURIER_EMPLOYEE));

        // Convert employees to DTO format
        for (UserEntity user : userEntitiesByRoles) {
            userDtos.add(this.modelMapper.map(user, UserDto.class));
        }

        return userDtos.toString();
    }

    @Override
    public String getAllClients() {
        List<UserDto> userDtos = new ArrayList<>();

        // Fetch all clients
        List<UserEntity> userEntitiesByRoles = this.userRepository
                .findUserEntitiesByRoles(Set.of(Role.CLIENT));

        // Convert clients to DTO format
        for (UserEntity user : userEntitiesByRoles) {
            userDtos.add(this.modelMapper.map(user, UserDto.class));
        }

        return userDtos.toString();
    }

    @Override
    public boolean pay(String username, Long packageId) {
        // Retrieve user by username
        UserEntity user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User with username " + username + " not found!"));

        // Retrieve package by ID
        Package pack = this.packageRepository
                .findById(packageId).orElseThrow(() -> new ObjectNotFoundException("Package with id " + packageId + " not found!"));

        // Ensure package is not already delivered
        if (pack.getState().equals(State.NOT_DELIVERED)) {
            // Check if user has sufficient balance
            if (user.getBalance() >= pack.getPrice()) {
                user.setBalance(user.getBalance() - pack.getPrice());
                pack.setPackagePaidStatus(PackagePaidStatus.PAID);
                // update company's revenue
                user.getLogisticCompany().setRevenue(user.getLogisticCompany().getRevenue() + pack.getPrice());
                // Save updated user and package
                this.userRepository.save(user);
                this.packageRepository.save(pack);
                return true;
            } else {
                return false; // Insufficient funds
            }
        } else {
            throw new ObjectNotFoundException(String.format("Package with id %d already delivered", packageId));
        }
    }

    @Override
    public boolean isUserExistingByEmailOrUsername(String email, String username) {
        // Check if a user exists by email or username
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);

        return byUsername.isPresent() || byEmail.isPresent();
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        // Assign default client role
        RoleEntity clientRole = roleRepository.findByRole(Role.CLIENT);

        // Map service model to entity
        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);

        // Encode password before saving
        userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        userEntity.setRoles(Set.of(clientRole));

        // Save user to repository
        userEntity = userRepository.save(userEntity);

        // Authenticate the new user
        UserDetails principal = logisticCompanyUserService.loadUserByUsername(userEntity.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                userEntity.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void initializeUsers() {
        // Fetch roles from repository
        RoleEntity adminRole = roleRepository.findByRole(Role.ADMIN);
        RoleEntity clientRole = roleRepository.findByRole(Role.CLIENT);
        RoleEntity courierEmployeeRole = roleRepository.findByRole(Role.COURIER_EMPLOYEE);
        RoleEntity officeEmployeeRole = roleRepository.findByRole(Role.OFFICE_EMPLOYEE);

        // Create and save admin user
        UserEntity admin = new UserEntity("vladko", "Vlado", "Dobrev",
                passwordEncoder.encode("1234"), "vlado@gmail.com", 150.0, 42, LocalDate.now(), "Armenia");
        admin.getRoles().add(adminRole);
        userRepository.save(admin);

        // Create and save client user
        UserEntity gosho = new UserEntity("Gosho", "Georgi", "Georgiev",
                passwordEncoder.encode("1234"), "gosho@abv.bg", 2000.0, 21, LocalDate.now(), "Cheeseburger");
        gosho.getRoles().add(clientRole);
        userRepository.save(gosho);

        // Create and save office employee
        UserEntity petur = new UserEntity("Pesho", "Petur", "Petrov",
                passwordEncoder.encode("1234"), "petur@abv.bg", 300.0, 25, LocalDate.now(), "Pizza");
        petur.getRoles().add(officeEmployeeRole);
        userRepository.save(petur);

        // Create and save courier employee
        UserEntity ivan = new UserEntity("Ivancho", "Ivan", "Ivanov",
                passwordEncoder.encode("1234"), "ivan@abv.bg", 465.3, 30, LocalDate.now(), "Sushi");
        ivan.getRoles().add(courierEmployeeRole);
        userRepository.save(ivan);

        // Print employees and clients
        System.out.println(this.getAllEmployees());
        System.out.println(this.getAllClients());
    }

    @Override
    public UserBalanceViewModel getLoggedUserInfo(String name) {
        // Retrieve user by username
        UserEntity user = userRepository.findByUsername(name)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        // Map user entity to balance view model
        return modelMapper.map(user, UserBalanceViewModel.class);
    }

    @Override
    public UserViewModel getUserInfo(String username) {
        // Retrieve user by username and map to user view model
        return userRepository
                .findByUsername(username)
                .map(userEntity -> modelMapper.map(userEntity, UserViewModel.class))
                .orElseThrow(() -> new ObjectNotFoundException("User with name " + username + " not found!"));
    }

    @Override
    public void addMoneyToUser(String username) {
        // Retrieve user by username
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        // Add a fixed amount to balance
        double balance = user.getBalance();
        balance += 1000;
        user.setBalance(balance);

        // Save updated balance
        userRepository.save(user);
    }

    @Override
    public void createEmployee(EmployeeServiceModel employeeServiceModel, String username) {
        UserEntity userEntity = modelMapper.map(employeeServiceModel, UserEntity.class);

        LogisticCompany logisticCompanyRepositoryByName = logisticCompanyRepository.findByName(employeeServiceModel.getCompany());

        RoleEntity role = roleRepository.findByRole(Role.valueOf(employeeServiceModel.getEmployeeType()));

        userEntity.setBalance(0.0);
        userEntity.setRoles(Set.of(role));
        userEntity.setLogisticCompany(logisticCompanyRepositoryByName);
        userEntity.setPassword(passwordEncoder.encode(employeeServiceModel.getPassword()));

        userRepository.save(userEntity);

        logisticCompanyRepositoryByName.getUserEntities().add(userEntity);
        logisticCompanyRepository.save(logisticCompanyRepositoryByName);
    }
}