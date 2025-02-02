package org.logisticcompany.service.Impl;

import org.logisticcompany.model.UserEntity;
import org.logisticcompany.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogisticCompanyUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public LogisticCompanyUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by username in the repository
        UserEntity entity = userRepository
                .findByUsername(username)
                // Throw an exception if the user is not found
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));

        // Convert the UserEntity to a UserDetails object
        return mapToUserDetails(entity);
    }

    private static UserDetails mapToUserDetails(UserEntity user) {
        // Convert user roles to a list of GrantedAuthority objects
        List<GrantedAuthority> grantedAuthorities = user
                .getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                .collect(Collectors.toList());

        // Return a new UserDetails object with username, password, and roles
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}