package org.logisticcompany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.Impl.LogisticCompanyUserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogisticCompanyUserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LogisticCompanyUserServiceImpl userService;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        RoleEntity role = new RoleEntity();
        role.setRole(org.logisticcompany.model.enums.Role.CLIENT);

        userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("password123");
        userEntity.setRoles(Set.of(role));
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertFalse(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknownUser"));
    }
}
