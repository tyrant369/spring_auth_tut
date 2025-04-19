package com.Authentication.AuthenticationService.config;

import com.Authentication.AuthenticationService.config.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(customizer -> customizer.disable())
        .authorizeHttpRequests(request -> request
                .requestMatchers("register", "signing")
                .permitAll()
                .anyRequest()
                .authenticated())
//        .formLogin(Customizer.withDefaults())
        //for post man or kind a things
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public MyUserDetailsService userDetailsService() {
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("Debu")
//                .password("D@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("pachu")
//                .password("P@123")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}