package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.service.VehicleService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @PostMapping
    public ResponseEntity<StandResponse> save(
            @RequestParam String vehicleName,
            @RequestParam double vehiclePriceFor1Km,
            @RequestParam String vehicleCategory,
            @RequestParam String vehicleType,
            @RequestParam double vehiclePriceFor100Km,
            @RequestParam String vehicleFuelType,
            @RequestParam int vehicleSeatCapacity,
            @RequestParam double vehicleFuelUsage,
            @RequestParam String vehicleHybrid,
            @RequestParam String vehicleTransmission,
            @RequestParam int vehicleQty,
            @RequestParam List<MultipartFile> vehicleImages
    ) throws IOException {
        ArrayList<byte[]> bytes = new ArrayList<>();
        vehicleImages.forEach(data->{
            try {
                bytes.add(data.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        RequestVehicleDto requestVehicleDto = new RequestVehicleDto(
                vehicleName,
                vehiclePriceFor1Km,
                vehicleCategory,
                vehicleType,
                vehiclePriceFor100Km,
                vehicleFuelType,
                vehicleSeatCapacity,
                vehicleFuelUsage,
                vehicleHybrid,
                vehicleTransmission,
                bytes,
                vehicleQty

        );

        ResponseVehicleDto save = vehicleService.save(requestVehicleDto);
        return new ResponseEntity<>(
                new StandResponse(201, "Customer delete", save), HttpStatus.CREATED
        );
    }
}
