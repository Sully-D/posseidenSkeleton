package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Users getUser(String username){

        Optional<Users> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new RuntimeException("Username not found !");

        return optionalUser.get();
    }

    /**
     * Retrieves a User by its ID.
     *
     * @param id the ID of the User to retrieve.
     * @return an Optional containing the Users if found, or empty if not found.
     * @throws IllegalArgumentException if the ID is not valid.
     */
    public Optional<Users> getUserById(Integer id) {
        Utils.intIsValide(id, "id user");
        return userRepository.findById(id);
    }

    /**
     * Retrieves all Users.
     *
     * @return a List of all Users entities.
     */
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Adds a new Users.
     *
     * @param user the Users entity to add.
     */
    public void addUser(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Updates an existing User by its ID.
     *
     * @param id the ID of the Rating to update.
     * @param updateUser the updated User entity.
     * @throws RuntimeException if no User is found with the given ID.
     * @throws IllegalArgumentException if the ID is not valid.
     */
    public void updateUser(int id, Users updateUser) {
        Utils.intIsValide(id, "id user");

        // Retrieve the existing Users
        Optional<Users> optionalUser = getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("No rating found with this id : " + id);
        }

        Users user = optionalUser.get();

        // Update the fields of the existing Rating
        user.setFullname(updateUser.getFullname());
        user.setUsername(updateUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(updateUser.getRole());

        // Save the updated Rating back to the repository
        userRepository.save(user);
    }

    /**
     * Deletes a User by its ID.
     *
     * @param id the ID of the Users to delete.
     * @throws RuntimeException if no Users is found with the given ID.
     * @throws IllegalArgumentException if the ID is not valid.
     */
    public void deleteUser(int id) {
        Utils.intIsValide(id, "id user");

        // Retrieve the existing User
        Optional<Users> optionalUser = getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("No user found with this id : " + id);
        }

        Users user = optionalUser.get();

        // Delete the User
        userRepository.delete(user);
    }
}
