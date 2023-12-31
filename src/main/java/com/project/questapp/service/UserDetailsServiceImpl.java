package com.project.questapp.service;

import com.project.questapp.entites.User;
import com.project.questapp.repos.UserRepository;
import com.project.questapp.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * bu service user details objesi dönecektir.
     */

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return JwtUserDetails.create(user);
    }
    public UserDetails loadUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return JwtUserDetails.create(user.get());
    }
}
