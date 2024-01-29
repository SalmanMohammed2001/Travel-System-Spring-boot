package com.travelserviceapi.travelserviceapi.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRole {

    @Id
    private String  propertyId;
    private String roleName;
    private String roleDescription;


    @OneToMany(mappedBy = "userRole")
    private List<User> users;
}
