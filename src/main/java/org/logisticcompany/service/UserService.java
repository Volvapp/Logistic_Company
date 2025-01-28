package org.logisticcompany.service;

import org.logisticcompany.model.service.UserServiceModel;

public interface UserService {
    boolean isUserExistingByEmailOrUsername(String email, String username);

    void registerUser(UserServiceModel userServiceModel);
}
