package org.logisticcompany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.UserDto;
import org.logisticcompany.model.enums.PackagePaidStatus;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.repository.LogisticCompanyRepository;
import org.logisticcompany.repository.PackageRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.Impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserEntity testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        // Create a test UserEntity with the new structure
        testUser = new UserEntity("Ivancho", "Ivan", "Ivanov",
                passwordEncoder.encode("1234"), "ivan@abv.bg", 465.3, 30, LocalDate.now(), "Sushi");

        // Create a test UserDto with the new structure
        testUserDto = new UserDto("Ivancho", "Ivan", "Ivanov", "1234", "ivan@abv.bg", 465.3, 30, LocalDate.now(), "Sushi");

    }

    @Test
    void testCreateUser() {
        // Create UserDto and mock behavior for conversion
        when(modelMapper.map(testUserDto, UserEntity.class)).thenReturn(testUser);
        when(userRepository.save(testUser)).thenReturn(testUser);

        UserEntity createdUser = userServiceImpl.createUser(testUserDto);

        assertNotNull(createdUser);
        assertEquals("Ivancho", createdUser.getUsername());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testGetUsers() {
        // Create UserDto and mock behavior for conversion
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(modelMapper.map(testUser, UserDto.class)).thenReturn(testUserDto);

        List<UserDto> users = userServiceImpl.getUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Ivancho", users.get(0).getUsername());
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;

        // Mocking behavior for updating a user
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);

        UserEntity updatedUser = userServiceImpl.updateUser(testUserDto, userId);

        assertNotNull(updatedUser);
        assertEquals("Ivancho", updatedUser.getUsername());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userServiceImpl.deleteUser(userId);

        assertEquals(0, userRepository.count());
    }

    @Test
    void testPayPackage_InsufficientBalance() {
        // Setup for package payment with insufficient balance
        UserEntity sender = new UserEntity("Gosho", "Georgi", "Georgiev", passwordEncoder.encode("1234"), "gosho@abv.bg", 40.0, 21, LocalDate.now(), "Cheeseburger");
        Package pack = new Package(sender, testUser, sender, "Some Address", 5.0, 50.0, PackageType.SENT);

        when(userRepository.findByUsername("Gosho")).thenReturn(Optional.of(sender));
        when(packageRepository.findById(1L)).thenReturn(Optional.of(pack));

        boolean paymentStatus = userServiceImpl.pay("Gosho", 1L);

        assertFalse(paymentStatus);
    }

    @Test
    void testIsUserExistingByEmailOrUsername() {
        // Mocking the behavior for checking if user exists by email or username
        when(userRepository.findByUsername("ivancho")).thenReturn(Optional.of(testUser));
        when(userRepository.findByEmail("ivan@abv.bg")).thenReturn(Optional.empty()); // Mock email check

        boolean exists = userServiceImpl.isUserExistingByEmailOrUsername("ivan@abv.bg", "ivancho");

        // Assert that the user exists, as the username check should return true
        assertTrue(exists);
    }

    @Test
    void testIsUserExistingByEmailOnly() {
        // Mocking the behavior for checking if user exists by email only
        when(userRepository.findByUsername("ivancho")).thenReturn(Optional.empty()); // Mock username check
        when(userRepository.findByEmail("ivan@abv.bg")).thenReturn(Optional.of(testUser));

        boolean exists = userServiceImpl.isUserExistingByEmailOrUsername("ivan@abv.bg", "ivancho");

        // Assert that the user exists, as the email check should return true
        assertTrue(exists);
    }

    @Test
    void testIsUserNotExisting() {
        // Mocking the behavior for when neither the email nor username exist
        when(userRepository.findByUsername("ivancho")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("ivan@abv.bg")).thenReturn(Optional.empty());

        boolean exists = userServiceImpl.isUserExistingByEmailOrUsername("ivan@abv.bg", "ivancho");

        // Assert that the user does not exist
        assertFalse(exists);
    }


    @Test
    void testAddMoneyToUser() {
        // Setup for adding money to user
        UserEntity user = new UserEntity("Ivancho", "Ivan", "Ivanov", passwordEncoder.encode("1234"), "ivan@abv.bg", 465.3, 30, LocalDate.now(), "Sushi");

        when(userRepository.findByUsername("ivan@abv.bg")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userServiceImpl.addMoneyToUser("ivan@abv.bg");

        assertEquals(1465.3, user.getBalance());
    }
}
