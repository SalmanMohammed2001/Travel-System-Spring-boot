package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestUserDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;

import java.io.IOException;

public interface UserService {
    public ResponseUserDto saveUser(RequestUserDto dto) throws IOException;
}
