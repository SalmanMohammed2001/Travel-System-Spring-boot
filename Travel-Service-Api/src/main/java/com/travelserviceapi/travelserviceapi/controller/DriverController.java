package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
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
           null ,
                driverName,
                driverContact,
                driverNic,
                driverImage,
                licenseImageFront,
                licenseImageRear,
                status,
            vehicleId
        );


    ResponseDriverDto save = driverService.save(requestDriverDto);
    return new ResponseEntity<>(
                new StandResponse(201, "drive saved", save), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "{nic}")
    public ResponseEntity<StandResponse> findByNic(@PathVariable String nic){
        ResponseDriverDto byNic = driverService.findByNic(nic);
        return new ResponseEntity<>(
                new StandResponse(201, "driver data",byNic ), HttpStatus.CREATED
        );
    }



    @PutMapping
    public ResponseEntity<StandResponse> update(
            @RequestParam String driverId,
            @RequestParam String driverName,
            @RequestParam String driverContact,
            @RequestParam String driverNic,
            @RequestPart byte[] driverImage,
            @RequestPart byte[] licenseImageFront,
            @RequestPart byte[] licenseImageRear,
            @RequestParam boolean status,
            @RequestParam String vehicleId

    ) throws IOException {


        RequestDriverDto requestDriverDto = new RequestDriverDto(
                driverId,
                driverName,
                driverContact,
                driverNic,
                driverImage,
                licenseImageFront,
                licenseImageRear,
                status,
                vehicleId
        );


     driverService.update(requestDriverDto);
        return new ResponseEntity<>(
                new StandResponse(201, "drive update", null), HttpStatus.CREATED
        );
    }

}
