package com.travelserviceapi.travelserviceapi.dto.core;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class BookingDetailsDto {
    private String bookingId;
    private String packageId;
    private String packageCategory;
    private String packageStartDate;
    private String packageEndDate;
    private double packageValue;
    private String userName;




}
