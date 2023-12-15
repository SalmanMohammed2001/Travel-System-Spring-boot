package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;

public interface VehicleService {
    public ResponseVehicleDto save(RequestVehicleDto dto);

}
