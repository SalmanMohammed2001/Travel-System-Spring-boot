package com.travelserviceapi.travelserviceapi.dto.responseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ResponseBookingDetailsDto {
    private String bookingId;

    private String packageId;

    private String date;

    private double total;

    private String guideName;

    private String vehicleType;




}
