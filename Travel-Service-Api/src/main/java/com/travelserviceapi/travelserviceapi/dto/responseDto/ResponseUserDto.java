package com.travelserviceapi.travelserviceapi.dto.responseDto;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.entity.Booking;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUserDto {

    private String userId;
    private String username;
    private String password;
    private String nic;
    private String dob;
    private String gender;
    @Embedded
    private Contact contact;
    private String email;
    private String address;
    private byte[] nicFrontImg;
    private byte[] nicRearImg;
    private byte[] profilePic;
    private List<Booking> bookings;


}
