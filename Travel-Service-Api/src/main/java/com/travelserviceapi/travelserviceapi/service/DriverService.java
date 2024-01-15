package com.travelserviceapi.travelserviceapi.service;


import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;

import java.io.IOException;
import java.util.List;

public interface DriverService {
    public ResponseDriverDto save(RequestDriverDto dto) throws IOException;

    public ResponseDriverDto findByNic(String nic);

    public void update(RequestDriverDto dto);

    public void deleteDriver(String id);

    public List<ResponseDriverDto> findAll() throws IOException;
}
