package com.untree.inventory_managment_opencv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests((requests) -> requests
                                                .requestMatchers(HttpMethod.POST, "/login", "/register",
                                                                "/static/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/login", "/register",
                                                                "/static/**")
                                                .permitAll()
                                                .anyRequest().authenticated());

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}

// .authorizeHttpRequests((requests) -> requests
// .requestMatchers("/", "/login", "/register", "/static/**").permitAll()
// .anyRequest().authenticated())
// .formLogin((form) -> form
// .loginPage("/login")
// .defaultSuccessUrl("/", true)
// .permitAll())
// .logout((logout) -> logout.permitAll());