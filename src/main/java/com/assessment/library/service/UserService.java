package com.assessment.library.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assessment.library.dto.UserDto;
import com.assessment.library.dto.request.UserPaginationRequest;
import com.assessment.library.enums.UserRole;
import com.assessment.library.model.User;
import com.assessment.library.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public Page<User> getAllusers(UserPaginationRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getSize());

        Page<User> userPage = userRepository.findByUsernameContainingIgnoreCaseOrRoleContainingIgnoreCase(
            request.getUsername() != null ? request.getUsername() : "",
            request.getRole() != null ? request.getRole() : "",
            pageable
        );

        return userPage;
    }

    @Cacheable(value = "users", key = "#id") 
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(UserDto userDto) {
        User user = new User(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @CachePut(value = "users", key = "#id")
    public User updateUser(Long id, UserDto updatedUser) {
        
        return userRepository.findById(id)
                .map(existinguser -> {
                    existinguser.setUsername(updatedUser.getUsername());
                    existinguser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    existinguser.setRole(updatedUser.getRole());
                    return userRepository.save(existinguser);
                })
                .orElseThrow(() -> new RuntimeException("user not found with id: " + id));
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public void createDefaultAdmins() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(1L,"admin", passwordEncoder.encode("admin"), UserRole.ADMIN);
            userRepository.save(admin);
        }
        if (userRepository.findByUsername("librarian").isEmpty()) {
            User librarian = new User(2L,"librarian", passwordEncoder.encode("librarian"), UserRole.LIBRARIAN);
            userRepository.save(librarian);
        }
    }
}

