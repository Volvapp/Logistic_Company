package org.logisticcompany.service.Impl;

import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.enums.Role;
import org.logisticcompany.model.service.UserServiceModel;
import org.logisticcompany.repository.RoleRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final LogisticCompanyUserServiceImpl logisticCompanyUserService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, LogisticCompanyUserServiceImpl logisticCompanyUserService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.logisticCompanyUserService = logisticCompanyUserService;
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
}
