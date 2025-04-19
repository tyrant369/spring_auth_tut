package com.Authentication.AuthenticationService.service;

import com.Authentication.AuthenticationService.model.UserPrinciple;
import com.Authentication.AuthenticationService.model.Users;
import com.Authentication.AuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrinciple(user);
    }
}
