package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUpdateBookingDto;

import java.io.IOException;
import java.util.List;

public interface BookingService {

    public void saveBooking(RequestBookingDto dto) throws IOException;
    public void updateBooking(RequestBookingDto dto) throws IOException;

    public List<ResponseBookingDto> findAll() throws IOException;

    public ResponseBookingDto findId(String id);

    public void  delete(String id);

    public ResponseUpdateBookingDto findUpdateId(String id) throws IOException;

}
