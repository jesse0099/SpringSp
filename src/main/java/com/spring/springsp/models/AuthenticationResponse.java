package com.spring.springsp.models;

import lombok.Getter;

import java.io.Serializable;

public class AuthenticationResponse  implements Serializable {
    @Getter
    private final String JWT;

    public AuthenticationResponse(String JWT) {
        this.JWT = JWT;
    }
}
