package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUser(String username){

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException("Username not found !");

        return optionalUser.get();
    }
}
