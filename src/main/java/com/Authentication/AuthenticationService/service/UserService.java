package com.Authentication.AuthenticationService.service;

import com.Authentication.AuthenticationService.model.Users;
import com.Authentication.AuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public Users register(Users user) {

        System.out.println("------in service--------");

        System.out.println(user);

        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public String verify(Users user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                ));

        return authentication.isAuthenticated() ? jwtService.generateToken(user.getUsername()) : "fail";
    }
}
