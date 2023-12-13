package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestUserDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.service.UserService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import jakarta.persistence.Embedded;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<StandResponse> saveUser(
            @RequestPart String username,
            @RequestPart String password,
            @RequestPart String nic,
            @RequestPart String dob,
            @RequestPart String gender,
            @RequestPart String contact1,
            @RequestPart String contact2,
            @RequestPart String email,
            @RequestPart String address,
            @RequestPart byte[] nicFrontImg,
            @RequestPart byte[] nicRearImg,
            @RequestPart byte[] profilePic

    ) throws IOException {
        Contact contact = new Contact(contact1,contact2);
        RequestUserDto requestUserDto = new RequestUserDto(
                username,
                password,
                nic,
                dob,
                gender,
                contact,
                email,
                address,
                nicFrontImg,
                nicRearImg,
                profilePic);


        ResponseUserDto responseUserDto = userService.saveUser(requestUserDto);
        return new ResponseEntity<>(
                new StandResponse(201, "Customer saved", responseUserDto), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "{email}")
    public ResponseEntity<StandResponse> findByUserEmail(@PathVariable String email) throws IOException {
        ResponseUserDto user = userService.findByUser(email);

        return new ResponseEntity<>(
                new StandResponse(201, "Customer saved", user), HttpStatus.CREATED
        );
    }
}
