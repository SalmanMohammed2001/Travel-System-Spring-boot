package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;

import java.io.IOException;
import java.util.List;

public interface VehicleService {
    public ResponseVehicleDto save(RequestVehicleDto dto) throws IOException;

    public ResponseVehicleDto findById(String id) throws IOException;

    public void  update(RequestVehicleDto dto) throws IOException;

    public List<ResponseVehicleDto> findAll() throws IOException;

    public void deleteById(String id);

    public List<ResponseVehicleDto> findAllVehicleStatesFalse() throws IOException;
    public List<ResponseVehicleDto> findAllVehicleStatesTrue(String category) throws IOException;

    public long  findAllVehicleCount();

}
