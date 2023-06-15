package com.project.questapp.controller;

import com.project.questapp.dto.request.CommentCreateRequest;
import com.project.questapp.dto.request.CommentResponse;
import com.project.questapp.dto.request.CommentUpdateRequest;
import com.project.questapp.entites.Comment;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping()
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId,postId);
    }
    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneComment(commentId);
    }
    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneComment(request);
    }
    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest updateRequest){
        return commentService.updateOneComment(commentId,updateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment (@PathVariable Long commentId){
        commentService.deleteOneComment(commentId);
    }


}
