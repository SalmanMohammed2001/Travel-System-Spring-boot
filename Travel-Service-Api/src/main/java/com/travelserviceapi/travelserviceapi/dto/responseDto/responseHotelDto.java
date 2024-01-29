package com.travelserviceapi.travelserviceapi.dto.responseDto;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.embadded.Price;
import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ResponseHotelDto {
    private String hotelId;
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
    private byte[] hotelFrontImage;
    private boolean hotelStatus;
    //private PackageDetails packageDetails;


}
