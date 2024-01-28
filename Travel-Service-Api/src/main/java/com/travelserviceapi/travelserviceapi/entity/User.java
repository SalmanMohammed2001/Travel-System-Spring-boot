package com.travelserviceapi.travelserviceapi.entity;


import com.travelserviceapi.travelserviceapi.embadded.Contact;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class User {
    @Id
    private String userId;
    private String username;
    @Column(unique = true,nullable = false)
    private String userEmail;
    private String UserPassword;
    @Column(unique = true,nullable = false)
    private String userNic;
    private String userDob;
    private String userGender;
    @Embedded
    private Contact userContact;
    private String userAddress;

    private String userNicFrontImg;
    private String userNicRearImg;
    private String userProfilePic;
    private boolean userState;

    public boolean isAccountNonExpired;
    public boolean isCredentialsNoExpired;
    public boolean isAccountNonLock;
    public boolean isEnable;

    @Column(unique = true)
    public  String prefix;
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;


    @ManyToOne()
    @JoinColumn(name = "role_id")
    private UserRole userRole;
}
