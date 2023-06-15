package com.project.questapp.dto.request;

import lombok.Data;

@Data
public class RefreshRequest {
    private Long userId;
    private String refreshToken;
}
