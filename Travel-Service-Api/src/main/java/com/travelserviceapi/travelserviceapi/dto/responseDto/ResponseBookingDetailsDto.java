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

    private String packageCategory;

    private String packageStartDate;

    private String packageEndDate;

    private double packageValue;

    private String userName;




}
