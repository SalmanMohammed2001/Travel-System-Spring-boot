package com.travelserviceapi.travelserviceapi.dto.requestDto;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestUserDto {

    private String username;
    private String password;
    private String nic;
    private String dob;
    private String gender;
    @Embedded
    private Contact contact;
    private String email;
    private String address;
    private boolean userState;
    private byte[] nicFrontImgByte;
    private byte[] nicRearImgByte;
    private byte[] profilePicByte;



}
