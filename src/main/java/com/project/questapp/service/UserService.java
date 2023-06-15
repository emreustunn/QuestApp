package com.project.questapp.service;

import com.project.questapp.entites.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userid) {
        return userRepository.findById(userid).orElse(null);
    }

    public User updateOneUser(Long userid, User newUser) {
        System.out.println("bu yeni avatar id = " + newUser.getAvatar());
        Optional<User> user = userRepository.findById(userid);
        if (user.isPresent()) {
            User foundUser = user.get();
            if(newUser.getUsername() != null && newUser.getPassword() != null) {
                foundUser.setUsername(newUser.getUsername());
                foundUser.setPassword(newUser.getPassword());
            }
            else if(newUser.getUsername() == null && newUser.getPassword()  == null){
                System.out.println("bu eski avatar id = " + user.get().getAvatar());
                foundUser.setAvatar(newUser.getAvatar());

            }
            userRepository.save(foundUser);
            return foundUser;
        } else {
            return null;
        }
    }

    public void deleteById(Long userid) {
        userRepository.deleteById(userid);
    }

    public User getOneUserByUserName(String username) {

        return userRepository.findByUsername(username);

    }


    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if (postIds.isEmpty()) return null;
        List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
