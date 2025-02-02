package org.logisticcompany.service;

import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.UserDto;
import org.logisticcompany.model.service.UserServiceModel;
import org.logisticcompany.model.view.UserBalanceViewModel;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserDto userDto);

    List<UserDto> getUsers();

    UserEntity updateUser(UserDto userDto, Long id);

    void deleteUser(Long id);

    String getAllEmployees();

    String getAllClients();

    boolean pay(String username, Long packageId);

    boolean isUserExistingByEmailOrUsername(String email, String username);

    void registerUser(UserServiceModel userServiceModel);

    void initializeUsers();

    UserBalanceViewModel getLoggedUserInfo(String name);
}
