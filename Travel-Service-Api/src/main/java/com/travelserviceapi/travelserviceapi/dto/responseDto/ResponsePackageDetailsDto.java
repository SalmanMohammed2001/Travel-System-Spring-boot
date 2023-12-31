package com.travelserviceapi.travelserviceapi.dto.responseDto;

import com.travelserviceapi.travelserviceapi.embadded.TravelDuration;
import com.travelserviceapi.travelserviceapi.entity.BookingDetails;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ResponsePackageDetailsDto {
    private String packageId;
    private String category;
    private TravelDuration travelDuration;
    private String packageTravelArea;
    private String packageNoAdult;
    private String packageNoChildren;
    private String PackageTotalHeadCount;
    private String withPetOrNo;
    private double packageValue;
    private boolean packageStatus;
    private ResponseHotelDto hotel;
    private ResponseVehicleDto vehicle;
    private List<BookingDetails> bookingDetailsLis;

}
