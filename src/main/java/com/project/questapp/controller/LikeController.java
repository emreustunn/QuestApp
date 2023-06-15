package com.project.questapp.controller;

import com.project.questapp.dto.request.LikeCreateRequest;
import com.project.questapp.dto.response.LikeResponse;
import com.project.questapp.entites.Like;
import com.project.questapp.service.LikeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    @GetMapping()
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return likeService.getAllLikesWithParam(userId,postId);
    }
    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId){
        return likeService.getOneLikeById(likeId);
    }
    @PostMapping()
    public Like createOneComment(@RequestBody LikeCreateRequest request){
        return likeService.createOneComment(request);
    }
    @DeleteMapping("/{likeId}")
    public void deleteOneLikeById(@PathVariable Long likeId){
        likeService.deleteOneLikeById(likeId);
    }
}
