package com.nnk.springboot.services;

import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Users getUser(String username){

        Optional<Users> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new RuntimeException("Username not found !");

        return optionalUser.get();
    }
}
