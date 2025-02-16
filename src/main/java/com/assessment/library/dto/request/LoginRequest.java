package com.assessment.library.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class LoginRequest {
    @NotEmpty(message = "username is required")
    private String username;
    
    @NotEmpty(message = "password is required")
    private String password;

    public LoginRequest(String username,String password){
        this.username = username;
        this.password=password;
    }
}
