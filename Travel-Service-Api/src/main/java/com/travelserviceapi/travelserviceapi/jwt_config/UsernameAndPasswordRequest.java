package com.travelserviceapi.travelserviceapi.jwt_config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAndPasswordRequest {

    private String username;
    private String password;
}
