package com.travelserviceapi.travelserviceapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(mappedBy = "userRole")
    private User user;
}