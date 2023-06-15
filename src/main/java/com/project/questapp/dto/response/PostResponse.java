package com.project.questapp.dto.response;

import com.project.questapp.entites.Like;
import com.project.questapp.entites.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String text;
    private String title;
    private List<LikeResponse> postLikes;

    public PostResponse(Post entity, List<LikeResponse> likes){
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUsername();
        this.text = entity.getText();
        this.title = entity.getTitle();
        this.postLikes = likes;
    }
}
