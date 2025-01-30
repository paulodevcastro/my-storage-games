package com.lit.my_storage_games_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth
                        -> auth.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated())
                .headers(header
                        -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(withDefaults()).httpBasic(withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")).build();
    }

    @Bean
    public InMemoryUserDetailsManager users(){
        UserDetails user = User.withUsername("user")
                .password("{noop}thunder")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}


