package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.core.UserDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestUserDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public ResponseUserDto saveUser(RequestUserDto dto) throws IOException;

    public ResponseUserDto findByUser(String email) throws IOException;
    public ResponseUserDto updateUser(RequestUserDto dto) throws IOException;

    public void deleteUser(String email);

    public List<ResponseUserDto> findAllUser() throws IOException;

    public List<ResponseUserDto> searchByEmailAndName(String email,String nic);

    public void VerifyAccount(String email,String otp);

    public long  findAllCustomerCount();

}
