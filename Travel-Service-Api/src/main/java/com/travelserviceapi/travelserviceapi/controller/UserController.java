package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestUserDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.service.UserService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/visitor/register")
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
          //  @RequestParam boolean userState,

            @RequestPart byte[] nicFrontImg,
            @RequestPart byte[] nicRearImg,
           @RequestPart byte[] profilePic

    ) throws IOException {
        System.out.println(username);
        System.out.println(password);
        System.out.println(nic);
        System.out.println(dob);
        System.out.println(gender);
        System.out.println(contact1);
        System.out.println(contact2);
        System.out.println(email);
        System.out.println(address);
        System.out.println(nicFrontImg);
        System.out.println(nicRearImg);
        System.out.println(profilePic);
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
                true,
                nicFrontImg,
                nicRearImg,
                profilePic);


       ResponseUserDto responseUserDto = userService.saveUser(requestUserDto);
        return new ResponseEntity<>(
                new StandResponse(201, "Customer saved", null), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "{email}")
   // @PreAuthorize("hasAuthority('customer:read')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<ResponseUserDto> findByUserEmail(@PathVariable String email) throws IOException {
        ResponseUserDto user = userService.findByUser(email);

       ArrayList<ResponseUserDto> responseUserDtos = new ArrayList<>();
            responseUserDtos.add(new ResponseUserDto(user.getUserId(),user.getUsername(),user.getPassword(),
                    user.getNic(),user.getDob(),user.getGender(),user.getContact(),user.getEmail(),
                    user.getAddress(),user.getNicFrontImg(),user.getNicRearImg(),user.getProfilePic(),user.isUserState(),
                    user.getNicFrontImgByte(),user.getNicRearImgByte(),user.getProfilePicByte()));
       return responseUserDtos;

       /* return new ResponseEntity<>(
                new StandResponse(201, "Customer saved", user), HttpStatus.CREATED
        )*/
    }

    @PutMapping
    @PreAuthorize("hasAuthority('customer:write')")
    public ResponseEntity<StandResponse> updateUser(
            @RequestPart String username,
            @RequestPart String password,
            @RequestPart String nic,
            @RequestPart String dob,
            @RequestPart String gender,
            @RequestPart String contact1,
            @RequestPart String contact2,
            @RequestPart String email,
            @RequestPart String address,
//            @RequestParam boolean userState,
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
                true,
                nicFrontImg,
                nicRearImg,
                profilePic);


        System.out.println(requestUserDto.getEmail());
        ResponseUserDto responseUserDto = userService.updateUser(requestUserDto);
        return new ResponseEntity<>(
                new StandResponse(201, "Customer update", null), HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = {"email"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<StandResponse> deleteUser( @RequestParam  String email){
        userService.deleteUser(email);
        return new ResponseEntity<>(
                new StandResponse(201, "Customer delete", null), HttpStatus.CREATED
        );
    }

    @GetMapping
 //   @PreAuthorize("hasAuthority('customer:read')")
    public ResponseEntity<StandResponse> findAllUser() throws IOException {
        List<ResponseUserDto> allUser = userService.findAllUser();

       return new ResponseEntity<>(
                new StandResponse(201, "Customer delete", allUser), HttpStatus.CREATED
        );
    }

    @GetMapping(params = {"text"})
    //@PreAuthorize("hasAuthority('customer:read')")
    public ResponseEntity<StandResponse> searchUser(@RequestParam String text) throws IOException {
        String searchText="%"+text+"%";
        List<ResponseUserDto> allUser = userService.searchByEmailAndName(text,text);

        return new ResponseEntity<>(
                new StandResponse(201, "Customer delete", allUser), HttpStatus.CREATED
        );
    }

    @PostMapping(value = "/visitor/verify/{otp}",params = "email")
    public ResponseEntity<StandResponse> verifyOtp(@RequestParam String email,@PathVariable String otp) throws IOException {
        userService.VerifyAccount(email,otp);
        return new ResponseEntity<>(
                new StandResponse(201, "Customer verified", ""), HttpStatus.CREATED
        );
    }


    @GetMapping(path = "count")
    public ResponseEntity<StandResponse> count() throws IOException {
        long allCustomerCount = userService.findAllCustomerCount();
        return new ResponseEntity<>(
                new StandResponse(201, "Customer verified", allCustomerCount), HttpStatus.CREATED
        );
    }




}
