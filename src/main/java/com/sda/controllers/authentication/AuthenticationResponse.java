package com.sda.controllers.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Long userId;
}
