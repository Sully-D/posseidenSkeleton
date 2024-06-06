package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
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

import java.util.List;
import java.util.Optional;

//import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Displays a list of all users.
     *
     * @param model the model to add attributes used for rendering view
     * @return the view name for displaying the list of users
     */
    @GetMapping("/users/list")
    public String home(Model model) {
        List<Users> listUsers = userService.getAllUsers();
        model.addAttribute("users", listUsers);
        return "/users/list";
    }

    /**
     * Displays the form for adding a new user.
     *
     * @param user the Users object to bind form data
     * @return the view name for the user add form
     */
    @GetMapping("/users/add")
    public String addUserForm(Users user) {
        return "users/add";
    }

    /**
     * Validates and saves a new user.
     *
     * @param user the Users object to validate and save
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the users list or add form if there are validation errors
     */
    @PostMapping("/users/validate")
    public String validate(@Valid Users user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/add";
        }
        userService.addUser(user);
        return "redirect:list";
    }

    /**
     * Displays the form for updating an existing user.
     *
     * @param id the ID of the user to update
     * @param model the model to add attributes used for rendering view
     * @return the view name for the rating update form or the users list if not found
     */
    @GetMapping("/users/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Users> users = userService.getUserById(id);

        if (users.isEmpty()) {
            model.addAttribute("error", "users not found");
            return "redirect:/users/list";
        }

        model.addAttribute("users", users.get());
        return "users/update";
    }

    /**
     * Validates and updates an existing user.
     *
     * @param id the ID of the user to update
     * @param users the Users object to validate and update
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the users list or update form if there are validation errors
     */
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid Users users,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", users);
            return "users/update";
        }

        userService.updateUser(id, users);
        return "redirect:/users/list";
    }

    /**
     * Deletes an existing user.
     *
     * @param id the ID of the user to delete
     * @param model the model to add attributes used for rendering view
     * @return the view name for the users list
     */
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUser(id);
        return "redirect:/users/list";
    }
}
