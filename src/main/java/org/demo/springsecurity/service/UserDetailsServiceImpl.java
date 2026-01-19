package org.demo.springsecurity.service;

import org.demo.springsecurity.entity.Users;
import org.demo.springsecurity.model.UserPrincipal;
import org.demo.springsecurity.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users users = userDetailsRepo.findByUsername(username);
            if (Objects.isNull(users)) {
                throw new UsernameNotFoundException("User not found");
            }
            return new UserPrincipal(users);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
