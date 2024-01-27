package com.travelserviceapi.travelserviceapi.dto.responseDto;

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
    private String user;
    private List<ResponseBookingDetailsDto> bookingDetailsLis;



}
