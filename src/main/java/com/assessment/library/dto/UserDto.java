package com.assessment.library.dto;

import com.assessment.library.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    @NotEmpty(message = "username is required")
    private String username;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @NotEmpty(message = "password is required")
    private String password;
}
