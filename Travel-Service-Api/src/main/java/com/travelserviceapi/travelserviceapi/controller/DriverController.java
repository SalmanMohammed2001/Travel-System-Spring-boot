package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.service.DriverService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

@PostMapping
    public ResponseEntity<StandResponse> save(
            @RequestParam String vehicleId,
            @RequestParam String driverName,
            @RequestParam String driverContact,
            @RequestParam String driverNic,
            @RequestPart byte[] driverImage,
            @RequestPart byte[] licenseImageFront,
            @RequestPart byte[] licenseImageRear,
            @RequestParam boolean status

    ) throws IOException {


    RequestDriverDto requestDriverDto = new RequestDriverDto(
                driverName,
                driverContact,
                driverNic,
                driverImage,
                licenseImageFront,
                licenseImageRear,
                status,
            vehicleId
        );

    driverService.save(requestDriverDto);
        return new ResponseEntity<>(
                new StandResponse(201, "Vehicle update", null), HttpStatus.CREATED
        );
    }

}
