package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestHotelDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseHotelDto;
import com.travelserviceapi.travelserviceapi.entity.Hotel;

import java.io.IOException;
import java.util.List;

public interface HotelService {

    public void save(RequestHotelDto dto);
    public ResponseHotelDto findById(String id);

    public void updateHotel(RequestHotelDto dto);

    public void deleteHotel(String id);

    public List<ResponseHotelDto> findAll() throws IOException;

    public List<ResponseHotelDto> findAllByHotelCategoryEquals(String category) throws IOException;
}
