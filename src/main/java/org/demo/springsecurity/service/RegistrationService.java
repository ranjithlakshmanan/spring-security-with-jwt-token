package org.demo.springsecurity.service;

import org.demo.springsecurity.model.GetAllUsersModel;
import org.demo.springsecurity.model.ServiceResponse;
import org.demo.springsecurity.model.UserModel;

public interface RegistrationService {

    ServiceResponse login(UserModel userModel);

    ServiceResponse updateUser(UserModel userModel);

    ServiceResponse registerUser(UserModel userModel);

    ServiceResponse getUserByUsername(String username);

    ServiceResponse getAllUsers(GetAllUsersModel getAllUsersModel);

}
