package com.alness.findcat.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.alness.findcat.auth.filters.JwtAuthenticationFilter;
import com.alness.findcat.auth.filters.JwtValidationFilter;
import com.alness.findcat.common.enums.AllowedProfiles;

@Configuration
public class SpringSecurytyConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                //.requestMatchers(HttpMethod.GET, "/users").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                //.requestMatchers(HttpMethod.POST, "/trucks/**").hasAnyAuthority(AllowedProfiles.ADMIN.getName())
                //.requestMatchers(HttpMethod.GET, "/trucks/**").hasAnyAuthority(AllowedProfiles.ADMIN.getName())
                //.requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyAuthority(AllowedProfiles.ADMIN.getName(), AllowedProfiles.USER.getName())
                //.requestMatchers(HttpMethod.GET, "/users/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority(AllowedProfiles.ADMIN.getName())
                .requestMatchers(HttpMethod.POST, "/users").hasAnyAuthority(AllowedProfiles.ADMIN.getName())
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
