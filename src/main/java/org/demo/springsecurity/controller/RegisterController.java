package org.demo.springsecurity.controller;

import org.demo.springsecurity.model.GetAllUsersModel;
import org.demo.springsecurity.model.ServiceResponse;
import org.demo.springsecurity.model.UserModel;
import org.demo.springsecurity.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegisterController {

    private final RegistrationService registrationService;

    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ServiceResponse> registerUser(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(registrationService.registerUser(userModel));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ServiceResponse> updateUser(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(registrationService.updateUser(userModel));
    }

    @PostMapping("/getUserByUsername")
    public ResponseEntity<ServiceResponse> getUserByUsername(@RequestBody String username) {
        return ResponseEntity.ok(registrationService.getUserByUsername(username.replace("\"", "")));
    }

    @PostMapping("/getAllUsers")
    public ResponseEntity<ServiceResponse> getAllUsers(@RequestBody GetAllUsersModel getAllUsersModel) {
        return ResponseEntity.ok(registrationService.getAllUsers(getAllUsersModel));
    }

    @PostMapping("/login")
    public ResponseEntity<ServiceResponse> login(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(registrationService.login(userModel));
    }
}
