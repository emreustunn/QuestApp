package com.project.questapp.dto.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private Long userId;
    private String accessToken;
    private String refreshToken;

}
