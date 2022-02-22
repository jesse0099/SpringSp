package com.spring.springsp.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Need default constructor for JSON parsing
    public AuthenticationRequest(){}

}
