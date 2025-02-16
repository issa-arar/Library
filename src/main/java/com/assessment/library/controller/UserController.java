package com.assessment.library.controller;


import com.assessment.library.dto.ApiResponse;
import com.assessment.library.dto.PaginationApiResponse;
import com.assessment.library.dto.UserDto;
import com.assessment.library.dto.request.UserPaginationRequest;
import com.assessment.library.model.User;
import com.assessment.library.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PaginationApiResponse<Page<User>>> getAllusers( @Validated @ModelAttribute UserPaginationRequest request) {

        Page<User> users = userService.getAllusers(request);

        PaginationApiResponse<Page<User>> response = new PaginationApiResponse<>(
                "users retrieved successfully",
                users,
                true,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getuserById(@PathVariable Long id) {
            Optional<User> user = userService.getUserById(id);
            if(user.isEmpty()){
                throw new RuntimeException("user Not Found");
            }
            return ResponseEntity.ok(new ApiResponse("user Fethed!",user,true));        
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserDto user) {

        return ResponseEntity.ok(new ApiResponse("user created Successfully!",userService.createUser(user),true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {

        return ResponseEntity.ok(new ApiResponse("user Updated Successfully!",userService.updateUser(id, user),true));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("user Deleted Successfully!",true));
    }
}
