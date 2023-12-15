package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;

import java.io.IOException;

public interface VehicleService {
    public ResponseVehicleDto save(RequestVehicleDto dto) throws IOException;

    public ResponseVehicleDto findById(String id) throws IOException;

}
