package com.project.questapp.repos;


import com.project.questapp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
//Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjg2Njc4ODM4LCJleHAiOjE2ODY2Nzg4NDJ9.8ON9KmGeJtE45OUhXQh2D6YfR-kBGRKWG6e-6dd8ydsvg42KSLgBSZaRHhIR-8gADen2fhhIHS8YcDMwJBiAMQ
    //Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjg2Njc4NjcyLCJleHAiOjE2ODY2Nzg2NzZ9.SadWCaf-6vDpexyHqo3RyrfADY2_gUU7dGXwBTW-KDfC_4mUcThLkcw8X9FYdm6QbzxmvKMM6eJC9fbi4EWKUw
}
