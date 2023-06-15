package com.project.questapp.dto.request;

import lombok.Data;

@Data
public class LikeCreateRequest {
    private Long id;
    private Long userId;
    private Long postId;

}