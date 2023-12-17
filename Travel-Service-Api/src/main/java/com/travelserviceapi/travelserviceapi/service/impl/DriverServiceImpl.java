package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.repo.DriverRepo;
import com.travelserviceapi.travelserviceapi.service.DriverService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;

public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;

    private final ModelMapper mapper;

    private Generator generator;

    private Gson gson;
    public DriverServiceImpl(DriverRepo driverRepo, ModelMapper mapper, Generator generator, Gson gson) {
        this.driverRepo = driverRepo;
        this.mapper = mapper;
        this.generator = generator;
        this.gson = gson;
    }

    @Override
    public ResponseVehicleDto save(RequestVehicleDto dto) {
        return null;
    }
}
