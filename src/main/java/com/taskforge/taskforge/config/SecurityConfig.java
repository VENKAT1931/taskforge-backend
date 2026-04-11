package com.taskforge.taskforge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔥 ADMIN ONLY
                        .requestMatchers("/tasks").hasRole("ADMIN")      // POST create
                        .requestMatchers("/tasks/").hasRole("ADMIN")     // safety
                        .requestMatchers("/tasks/all").hasRole("ADMIN")  // optional future

                        // 🔥 USER + ADMIN
                        .requestMatchers("/tasks/my").authenticated()

                        // 🔥 GET ALL TASKS (ADMIN only)
                        .requestMatchers("/tasks").hasRole("ADMIN")

                        .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }

    // 🔥 In-memory users for testing
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(

                User.withUsername("venkat31v@gmail.com")
                        .password("{noop}admin123")
                        .roles("ADMIN")
                        .build(),

                User.withUsername("user")
                        .password("{noop}user123")
                        .roles("USER")
                        .build()
        );
    }
}