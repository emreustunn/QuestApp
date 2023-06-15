package com.project.questapp.dto.request;

import com.project.questapp.entites.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private String text;
    private String userName;

    public CommentResponse(Comment entity){
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.text = entity.getText();
    this.userName = entity.getUser().getUsername();
    }

}
