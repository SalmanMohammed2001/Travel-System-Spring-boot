package com.travelserviceapi.travelserviceapi.dto.core;

import com.travelserviceapi.travelserviceapi.entity.Booking;
import com.travelserviceapi.travelserviceapi.entity.BookingDetails_Pk;
import com.travelserviceapi.travelserviceapi.entity.PackageDetails;
import jakarta.persistence.*;
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
