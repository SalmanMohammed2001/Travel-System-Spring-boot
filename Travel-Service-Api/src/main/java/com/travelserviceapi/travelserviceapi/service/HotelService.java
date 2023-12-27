package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestHotelDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseHotelDto;

public interface HotelService {

    public void save(RequestHotelDto dto);
    public ResponseHotelDto findById(String id);
}
