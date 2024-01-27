package com.travelserviceapi.travelserviceapi.dto.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class RequestBookingDetailsDto {
    private String packageId;
    private String packageCategory;
    private String packageStartDate;
    private String packageEndDate;
    private double packageValue;

}


