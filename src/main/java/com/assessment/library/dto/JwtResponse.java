package com.assessment.library.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String username;
  private List<String> role;

  public JwtResponse(String accessToken,String username, List<String> role) {
    this.token = accessToken;
    this.username = username;
    this.role = role;
  }



  
}