package com.project.questapp.controller;

import com.project.questapp.dto.response.UserResponse;
import com.project.questapp.entites.User;
import com.project.questapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {
        return userService.saveOneUser(newUser);
    }

    @GetMapping("/{userid}")
    public UserResponse getOneUser(@PathVariable Long userid) {
        return new UserResponse(userService.getOneUserById(userid));
    }

    @PutMapping("/{userid}")
    public User updateOneUser(@PathVariable Long userid, @RequestBody User newUser) {
        return userService.updateOneUser(userid,newUser);

    }
    @DeleteMapping("/{userid}")
    public void deleteOneUser(@PathVariable Long userid){
        userService.deleteById(userid);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
       return userService.getUserActivity(userId);
    }
}
