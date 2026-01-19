package org.demo.springsecurity.repo;

import org.demo.springsecurity.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    Users findByUsername(String userName);

    boolean existsByUsername(String username);
}
