package com.travelserviceapi.travelserviceapi.dto.core;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class BookingDto {

    private String bookingId;
    private String bookingDate;
    private double bookingPrice;
    private byte[] bankSlip;
    private boolean bookingStatus;
    private String user;
    private List<BookingDetailsDto> bookingDetailsLis;




}
