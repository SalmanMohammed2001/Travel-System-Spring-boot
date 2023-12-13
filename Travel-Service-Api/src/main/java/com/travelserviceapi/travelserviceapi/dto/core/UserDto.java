package com.travelserviceapi.travelserviceapi.dto.core;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.entity.Booking;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

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
    private byte[] nicFrontImgByte;
    private byte[] nicRearImgByte;
    private byte[] profilePicByte;
    private List<Booking> bookings;


}
