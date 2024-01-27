package com.travelserviceapi.travelserviceapi.dto.core;

import com.travelserviceapi.travelserviceapi.embadded.TravelDuration;
import com.travelserviceapi.travelserviceapi.entity.BookingDetails;
import com.travelserviceapi.travelserviceapi.entity.Hotel;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
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


public class PackageDetailsDto {
    private String packageId;
    private String category;
    private TravelDuration travelDuration;
    private String packageTravelArea;
    private String packageNoAdult;
    private String packageNoChildren;
    private String PackageTotalHeadCount;
    private String withPetOrNo;
    private double packageValue;
    private String packageStatus;
    private String hotel;
    private String vehicle;

    private List<BookingDetails> bookingDetailsLis;


}
