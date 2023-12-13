package com.travelserviceapi.travelserviceapi.entity;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.embadded.Price;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Hotel {

    @Id
    private int hotelId;
    private String hotelName;
    private String hotelCategory;
    private String hotelPetAllowed;
    private String hotelMapLink;
    private String hotelAddress;
    @Embedded
    private Contact hotelContact;
    private String hotelEmail;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(
            name = "price_table",
            joinColumns = @JoinColumn(name = "hotelId" )
    )
    private Collection<Price> hotelPrices;

    private String images;
    private boolean hotelStatus;

    @OneToOne(mappedBy = "hotel")
    private PackageDetails  packageDetails;
}
