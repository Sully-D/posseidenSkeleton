package com.nnk.springboot.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Security configuration class for Spring Security.
 * This class configures security for web applications, including authentication and authorization mechanisms.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /**
     * Configures the security filter chain for HTTP requests.
     * Defines which endpoints are accessible to which roles.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers("/bidList/**").hasRole("ADMIN")
                        .requestMatchers("/curvePoint/**").hasRole("ADMIN")
                        .requestMatchers("/rating/**").hasRole("ADMIN")
                        .requestMatchers("/ruleName/**").hasRole("ADMIN")
                        .requestMatchers("/trade/**").hasRole("ADMIN")
                        .requestMatchers("/bidList/**").hasRole("USER")
                        .requestMatchers("/curvePoint/**").hasRole("USER")
                        .requestMatchers("/rating/**").hasRole("USER")
                        .requestMatchers("/ruleName/**").hasRole("USER")
                        .requestMatchers("/trade/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }


    /**
     * Configures in-memory user details for authentication.
     * This is a basic setup for demonstration purposes and should be replaced with a persistent store in production.
     *
     * @return The UserDetailsService containing the in-memory users.
     */
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER").build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("USER", "ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }


    /**
     * Bean for password encoding using BCrypt.
     * BCrypt is a strong hashing function that is recommended for password storage.
     *
     * @return The BCryptPasswordEncoder bean.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Configures the authentication manager with the custom user details service and password encoder.
     *
     * @param http The HttpSecurity object to configure.
     * @param bCryptPasswordEncoder The password encoder to use for encoding passwords.
     * @return The configured AuthenticationManager.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
