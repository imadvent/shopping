package com.shopping.config;

import com.shopping.filter.JwtFilter;
import com.shopping.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

//    @Bean  //it is for harcoded roles
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails admin = User.withUsername("admin")
//                .password(encoder.encode("adminpwd"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("user")
//                .password(encoder.encode("userpwd"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//
//    }

    @Bean
    public UserDetailsService userDetailsService() {

        return new CustomUserDetailsServiceImpl();
    }

//    @Bean  //it is for just spring security
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeHttpRequests().requestMatchers("/shopping/check", "/role", "/authenticate").permitAll()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/shopping/**", "/getrole").authenticated()
//                .and().formLogin()
//                .and().build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/shopping/check", "/role", "/authenticate", "/swagger-ui/**",
                        "/v3/api-docs/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/shopping/**", "/getrole").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
