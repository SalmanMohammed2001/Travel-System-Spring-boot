package com.travelserviceapi.travelserviceapi.jwt_config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserNameAndPasswordRequest {
    private String username;
  private   String password;
}
