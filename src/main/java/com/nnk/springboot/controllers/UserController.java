package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/users/add")
    public String addUser(Users bid) {
        return "users/add";
    }

    @PostMapping("/users/validate")
    public String validate(@Valid Users users, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            users.setPassword(encoder.encode(users.getPassword()));
            userRepository.save(users);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/users/list";
        }
        return "users/add";
    }

    @GetMapping("/users/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Users users = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        users.setPassword("");
        model.addAttribute("users", users);
        return "users/update";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid Users users,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/update";
        }

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        users.setPassword(encoder.encode(users.getPassword()));
//        users.setId(id);
        userRepository.save(users);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/users/list";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        Users users = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(users);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/users/list";
    }
}
