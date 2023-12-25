package com.travelserviceapi.travelserviceapi.dto.core;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.embadded.Price;
import com.travelserviceapi.travelserviceapi.entity.PackageDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class HotelDto {


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

    private ArrayList<byte[]> images;
    private boolean hotelStatus;


}
