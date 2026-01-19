package org.demo.springsecurity.service;

import org.demo.springsecurity.entity.Users;
import org.demo.springsecurity.exceptions.*;
import org.demo.springsecurity.model.GetAllUsersModel;
import org.demo.springsecurity.model.ServiceResponse;
import org.demo.springsecurity.model.TableResponse;
import org.demo.springsecurity.model.UserModel;
import org.demo.springsecurity.repo.UserDetailsRepo;
import org.demo.springsecurity.specification.UserSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private JWTService jwtService;

    private final UserDetailsRepo userDetailsRepo;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public RegistrationServiceImpl(UserDetailsRepo userDetailsRepo, AuthenticationManager authenticationManager) {
        this.userDetailsRepo = userDetailsRepo;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public ServiceResponse registerUser(UserModel userModel) {
        try {
            if (Objects.nonNull(userModel)) {
                Users users = userDetailsRepo.findByUsername(userModel.getUsername());
                if (Objects.nonNull(users)) {
                    throw new RegistrationException("User is found with given username");
                }
                users = new Users();
                BeanUtils.copyProperties(userModel, users, "id", "version");
                users.setPassword(encoder.encode(userModel.getPassword()));
                userDetailsRepo.save(users);
                return ServiceResponse.builder().success(true).response(Map.of("message", "Registered Successfully")).build();
            }
            return ServiceResponse.builder().success(false).build();
        } catch (RegistrationException ex) {
            throw new RegistrationException(ex.getMessage());
        } catch (DataIntegrityViolationException | ObjectOptimisticLockingFailureException ex) {
            throw new DataManipulationException(ex.getMessage());
        } catch (DataAccessException ex) {
            throw new CustomDBException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new SpringSecurityException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ServiceResponse updateUser(UserModel userModel) {
        try {
            if (Objects.nonNull(userModel)) {
                Users users = userDetailsRepo.findByUsername(userModel.getUsername());
                if (Objects.isNull(users)) {
                    throw new RegistrationException("User is not found with given username");
                }
                if (!Objects.equals(users.getUsername(), userModel.getUsername()) && userDetailsRepo.existsByUsername(userModel.getUsername())) {
                    throw new RegistrationException("User is found with given username");
                }
                BeanUtils.copyProperties(userModel, users, "id", "version");
                users.setPassword(encoder.encode(userModel.getPassword()));
                userDetailsRepo.save(users);
                return ServiceResponse.builder().success(true).response(Map.of("message", "Updated Successfully")).build();
            }
            return ServiceResponse.builder().success(false).build();
        } catch (RegistrationException ex) {
            throw new RegistrationException(ex.getMessage());
        } catch (DataIntegrityViolationException | ObjectOptimisticLockingFailureException ex) {
            throw new DataManipulationException(ex.getMessage());
        } catch (DataAccessException ex) {
            throw new CustomDBException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new SpringSecurityException(ex.getMessage());
        }
    }

    @Override
    public ServiceResponse getUserByUsername(String username) {
        try {
            if (Objects.nonNull(username) && !username.isBlank()) {
                Users users = userDetailsRepo.findByUsername(username);
                if (Objects.isNull(users)) {
                    throw new RegistrationException("User is not found with given username");
                }
                UserModel userModel = new UserModel();
                BeanUtils.copyProperties(users, userModel);
                return ServiceResponse.builder().success(true).response(userModel).build();
            }
            return ServiceResponse.builder().success(false).build();
        } catch (RegistrationException ex) {
            throw new RegistrationException(ex.getMessage());
        } catch (DataIntegrityViolationException | ObjectOptimisticLockingFailureException ex) {
            throw new DataManipulationException(ex.getMessage());
        } catch (DataAccessException ex) {
            throw new CustomDBException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new SpringSecurityException(ex.getMessage());
        }
    }

    @Override
    public ServiceResponse getAllUsers(GetAllUsersModel getAllUsersModel) {
        try {
            if (Objects.nonNull(getAllUsersModel)) {
                Sort sort = Objects.equals(getAllUsersModel.getSortOrder(), "asc") ?
                        Sort.by(getAllUsersModel.getSortField()).ascending() : Sort.by(getAllUsersModel.getSortField()).descending();
                Pageable pageable = PageRequest.of(getAllUsersModel.getPage(), getAllUsersModel.getPageSize(), sort);
                Specification<Users> usersSpecification = UserSpecification.getAllUsers(getAllUsersModel);
                Page<Users> usersPage = userDetailsRepo.findAll(usersSpecification, pageable);
                return ServiceResponse.builder().success(true).response(new TableResponse(usersPage.getContent().stream()
                        .map(user -> new UserModel(user.getId(), user.getUsername(), user.getPassword())),
                        usersPage.getTotalElements())).build();
            }
            return ServiceResponse.builder().success(false).build();
        } catch (RegistrationException ex) {
            throw new RegistrationException(ex.getMessage());
        } catch (DataIntegrityViolationException | ObjectOptimisticLockingFailureException ex) {
            throw new DataManipulationException(ex.getMessage());
        } catch (DataAccessException ex) {
            throw new CustomDBException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new SpringSecurityException(ex.getMessage());
        }
    }

    @Override
    public ServiceResponse login(UserModel userModel) {
        try {
//            if (Objects.nonNull(userModel) && !userModel.getUsername().isBlank() && !userModel.getPassword().isBlank()) {
//                Users users = userDetailsRepo.findByUsername(userModel.getUsername());
//                if(Objects.isNull(users)){
//                    throw new UserAuthenticationException("User is not registered. Please register to login");
//                }
//                if(encoder.matches(userModel.getPassword(), users.getPassword())){
//                    return ServiceResponse.builder().success(true).response(
//                            Map.of("message", "Logged in Successfully",
//                                    "token", "")
//                    ).build();
//                }
//            } else {
//                throw new UserAuthenticationException("Please provide all credentials");
//            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userModel.getUsername(), userModel.getPassword()));
            if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
                return ServiceResponse.builder().success(true).response(
                        Map.of("message", "Logged in Successfully",
                                "token", jwtService.generateToken(userModel.getUsername()))
                ).build();
            }
            throw new UserAuthenticationException("Login exception");
        } catch (RegistrationException ex) {
            throw new RegistrationException(ex.getMessage());
        } catch (DataIntegrityViolationException | ObjectOptimisticLockingFailureException ex) {
            throw new DataManipulationException(ex.getMessage());
        } catch (DataAccessException ex) {
            throw new CustomDBException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new SpringSecurityException(ex.getMessage());
        }
    }
}
