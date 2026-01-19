package org.demo.springsecurity.specification;

import jakarta.persistence.criteria.Predicate;
import org.demo.springsecurity.entity.Users;
import org.demo.springsecurity.model.GetAllUsersModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public UserSpecification() {
    }

    public static Specification<Users> getAllUsers(GetAllUsersModel getAllUsersModel) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!getAllUsersModel.getGlobalFilter().isBlank()) {
                predicateList.add(cb.like(
                        root.get("username"), getAllUsersModel.getGlobalFilter()
                ));
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };

    }
}
