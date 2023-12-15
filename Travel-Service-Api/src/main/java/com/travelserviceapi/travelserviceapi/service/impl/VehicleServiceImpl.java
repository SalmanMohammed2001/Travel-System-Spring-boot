package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.service.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Override
    public ResponseVehicleDto save(RequestVehicleDto dto) {
        return null;
    }
}
