package com.assessment.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assessment.library.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {
        @Autowired
        private final UserRepository userRepository;


        public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

      @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
