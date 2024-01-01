package com.travelserviceapi.travelserviceapi.dto.responseDto;

import com.travelserviceapi.travelserviceapi.dto.core.BookingDetailsDto;
import com.travelserviceapi.travelserviceapi.entity.BookingDetails;
import com.travelserviceapi.travelserviceapi.entity.Guide;
import com.travelserviceapi.travelserviceapi.entity.User;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ResponseBookingDto {


    private String bookingId;
    private String bookingDate;
    private double bookingPrice;
    private byte[] bankSlip;
    private boolean bookingStatus;
    private String guide;
    private String user;
    private List<ResponseBookingDetailsDto> bookingDetailsLis;



}
