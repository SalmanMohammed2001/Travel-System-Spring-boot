package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDto;

import java.io.IOException;

public interface BookingService {

    public void saveBooking(RequestBookingDto dto) throws IOException;
}
