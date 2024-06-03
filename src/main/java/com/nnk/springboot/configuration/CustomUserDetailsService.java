package com.nnk.springboot.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository dbUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.nnk.springboot.domain.User> optionalUser = dbUserRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found !");
        }
        com.nnk.springboot.domain.User user = optionalUser.get();

        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}


//import com.nnk.springboot.domain.User;
//import com.nnk.springboot.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
///**
// * Custom implementation of the UserDetailsService interface for authentication with Spring Security.
// * This service is used by Spring Security during the authentication process to load user-specific data.
// */
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    /**
//     * Loads the user's data from the database using the email as the username.
//     *
//     * @param username the username to retrieve user details.
//     * @return User containing the username, password, and authorities.
//     * @throws UsernameNotFoundException if no user is found with the given username.
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Fetching user from the repository
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No user found : " + username));
//
//        // Returning user details required by Spring Security for authentication and authorization
//        return (UserDetails) user;
//    }
//
//    /**
//     * Helper method to create a list of GrantedAuthority objects from a user role.
//     * Prefixes each role with "ROLE_" which is a convention used by Spring Security.
//     *
//     * @param role The security role of the user.
//     * @return A list of GrantedAuthority objects representing the user's security roles.
//     */
//    private List<GrantedAuthority> getGrantedAuthorities(String role) {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//        return authorities;
//    }
//}