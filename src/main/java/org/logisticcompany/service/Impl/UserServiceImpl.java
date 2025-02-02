package org.logisticcompany.service.Impl;

import org.logisticcompany.model.Package;
import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.UserDto;
import org.logisticcompany.model.enums.Role;
import org.logisticcompany.model.enums.State;
import org.logisticcompany.model.service.UserServiceModel;
import org.logisticcompany.repository.PackageRepository;
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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PackageRepository packageRepository, ModelMapper modelMapper, LogisticCompanyUserServiceImpl logisticCompanyUserService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.packageRepository = packageRepository;
        this.modelMapper = modelMapper;
        this.logisticCompanyUserService = logisticCompanyUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity createUser(UserDto userDto) {
        UserEntity user = this.modelMapper.map(userDto, UserEntity.class);

        this.userRepository.save(user);
        log.info(String.format("User: %s created", user.getUsername()));
        return user;
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> users = this.userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity user : users) {
            userDtos.add(this.modelMapper.map(user, UserDto.class));
        }
        return userDtos;
    }

    @Override
    public UserEntity updateUser(UserDto userDto, Long id) {
        UserEntity user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id: %d not found", id)));
        this.modelMapper.map(userDto, user);
        this.userRepository.save(user);
        log.info(String.format("User: %s updated", user.getUsername()));
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
        log.info(String.format("User with id: %s deleted", id));
    }

    @Override
    public String getAllEmployees() {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntitiesByRoles = this.userRepository
                .findUserEntitiesByRoles(Set.of(Role.OFFICE_EMPLOYEE, Role.COURIER_EMPLOYEE));

        for (UserEntity user : userEntitiesByRoles) {
            userDtos.add(this.modelMapper.map(user, UserDto.class));
        }

        return userDtos.toString();
    }

    @Override
    public String getAllClients() {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntitiesByRoles = this.userRepository
                .findUserEntitiesByRoles(Set.of(Role.CLIENT));

        for (UserEntity user : userEntitiesByRoles) {
            userDtos.add(this.modelMapper.map(user, UserDto.class));
        }

        return userDtos.toString();
    }

    @Override
    public boolean pay(String username, Long packageId) {
        UserEntity user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User with username " + username + " not found!"));

        Package pack = this.packageRepository
                .findById(packageId).orElseThrow(() -> new ObjectNotFoundException("Package with id " + packageId + " not found!"));

        if (pack.getState().equals(State.NOT_DELIVERED)) {

            if (user.getBalance() >= pack.getPrice()) {
                user.setBalance(user.getBalance() - pack.getPrice());

                this.userRepository.save(user);
                this.packageRepository.save(pack);
                return true;
            } else {
                return false;
            }
        } else {
            throw new ObjectNotFoundException(String.format("Package with id %d already delivered", packageId));
        }
    }


    @Override
    public boolean isUserExistingByEmailOrUsername(String email, String username) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);

        return byUsername.isPresent() || byEmail.isPresent();
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        RoleEntity clientRole = roleRepository.findByRole(Role.CLIENT);

        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);

        userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        userEntity.setRoles(Set.of(clientRole));

        userEntity = userRepository.save(userEntity);

        UserDetails principal = logisticCompanyUserService.loadUserByUsername(userEntity.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                userEntity.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

    @Override
    public void initializeUsers() {

        RoleEntity adminRole = roleRepository.findByRole(Role.ADMIN);
        RoleEntity clientRole = roleRepository.findByRole(Role.CLIENT);
        RoleEntity courierEmployeeRole = roleRepository.findByRole(Role.COURIER_EMPLOYEE);
        RoleEntity officeEmployeeRole = roleRepository.findByRole(Role.OFFICE_EMPLOYEE);

        UserEntity admin = new UserEntity("vladko", "Vlado", "Dobrev",
                passwordEncoder.encode("1234"), "vlado@gmail.com", 150.0, 42, LocalDate.now(), "Armenia");
        admin.getRoles().add(adminRole);

        userRepository.save(admin);

        UserEntity gosho = new UserEntity("Gosho", "Georgi", "Georgiev",
                passwordEncoder.encode("1234"), "gosho@abv.bg", 2000.0, 21, LocalDate.now(), "Cheeseburger");
        gosho.getRoles().add(clientRole);
        userRepository.save(gosho);

        UserEntity petur = new UserEntity("Pesho", "Petur", "Petrov",
                passwordEncoder.encode("1234"), "petur@abv.bg", 300.0, 25, LocalDate.now(), "Pizza");
        petur.getRoles().add(courierEmployeeRole);
        userRepository.save(petur);

        UserEntity ivan = new UserEntity("Ivancho", "Ivan", "Ivanov",
                passwordEncoder.encode("1234"), "ivan@abv.bg", 465.3, 30, LocalDate.now(), "Sushi");
        ivan.getRoles().add(officeEmployeeRole);
        userRepository.save(ivan);

        System.out.println(this.getAllEmployees());
        System.out.println(this.getAllClients());
    }
}
