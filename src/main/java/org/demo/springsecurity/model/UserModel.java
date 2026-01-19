package org.demo.springsecurity.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

    private Long id;
    private String username;
    private String password;

    public UserModel() {
    }

    public UserModel(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
