package com.nnk.springboot.configuration;

import java.util.ArrayList;
import java.util.List;

import com.nnk.springboot.domain.Users;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the UserDetailsService interface for authentication with Spring Security.
 * This service is used by Spring Security during the authentication process to load user-specific data.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads the user's data from the database using the username.
     * This method is called by Spring Security during the authentication process.
     *
     * @param username The username used to retrieve user details.
     * @return UserDetails containing the user's username, password, and authorities.
     * @throws UsernameNotFoundException if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user details from the user service
        Users user = userService.getUser(username);

        // Return a UserDetails object containing the user's data
        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    /**
     * Converts a user's role to a list of GrantedAuthority objects.
     * This is used by Spring Security for role-based access control.
     *
     * @param role The security role of the user.
     * @return A list of GrantedAuthority objects representing the user's security roles.
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        // Prefix the role with "ROLE_" as a convention used by Spring Security
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return authorities;
    }
}