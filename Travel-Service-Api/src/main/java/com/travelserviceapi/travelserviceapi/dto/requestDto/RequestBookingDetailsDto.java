package com.travelserviceapi.travelserviceapi.dto.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class RequestBookingDetailsDto {
    private String bookingId;

    private String packageId;

    private String date;

    private double total;

    private String guideName;

    private String vehicleType;




}
