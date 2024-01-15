package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
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
@CrossOrigin
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
            @RequestPart byte[] licenseImageRear
//            @RequestParam boolean status

    ) throws IOException {


    RequestDriverDto requestDriverDto = new RequestDriverDto(
           null ,
                driverName,
                driverContact,
                driverNic,
                driverImage,
                licenseImageFront,
                licenseImageRear,
                true,
            vehicleId,
            null
        );


    ResponseDriverDto save = driverService.save(requestDriverDto);
    return new ResponseEntity<>(
                new StandResponse(201, "drive saved", save), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "{nic}")
    public List<ResponseDriverDto> findByNic(@PathVariable String nic){
        ResponseDriverDto dto = driverService.findByNic(nic);

        List<ResponseDriverDto> responseDriverDtos = new ArrayList<>();
        responseDriverDtos.add(new ResponseDriverDto(
                dto.getDriverId(),
                dto.getDriverName(),
                dto.getDriverContact(),
                dto.getDriverNic(),
                dto.getDriverImage(),
                dto.getLicenseImageFront(),
                dto.getLicenseImageRear(),
                dto.isDriverStatus(),
                new ResponseVehicleDto(
                       dto.getVehicle().getVehicleId(),
                       dto.getVehicle().getVehicleName(),
                        dto.getVehicle().getVehiclePriceFor1Km(),
                        dto.getVehicle().getVehicleCategory(),
                        dto.getVehicle().getVehicleType(),
                        dto.getVehicle().getVehiclePriceFor100Km(),
                        dto.getVehicle().getVehicleFuelType(),
                        dto.getVehicle().getVehicleSeatCapacity(),
                        dto.getVehicle().getVehicleFuelUsage(),
                        dto.getVehicle().getVehicleHybrid(),
                        dto.getVehicle().getVehicleTransmission(),
                        dto.getVehicle().getVehicleImages(),
                        dto.getVehicle().getVehicleQty(),
                        dto.getVehicle().isVehicleState()
                )
        ));


        return responseDriverDtos;



       /* return new ResponseEntity<>(
                new StandResponse(201, "driver data",byNic ), HttpStatus.CREATED
        )*/
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
            @RequestParam String vehicleId,
            @RequestParam String oldVehicleId

    ) throws IOException {


        RequestDriverDto requestDriverDto = new RequestDriverDto(
                driverId,
                driverName,
                driverContact,
                driverNic,
                driverImage,
                licenseImageFront,
                licenseImageRear,
                true,
                vehicleId,
                oldVehicleId
        );


     driverService.update(requestDriverDto);
        return new ResponseEntity<>(
                new StandResponse(201, "drive update", null), HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<StandResponse> delete(@RequestParam String id){
        driverService.deleteDriver(id);
        return new ResponseEntity<>(
                new StandResponse(201, "drive update", null), HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<StandResponse> findAll() throws IOException {
        List<ResponseDriverDto> all = driverService.findAll();
        return new ResponseEntity<>(
                new StandResponse(201, "all Driver ", all), HttpStatus.CREATED
        );
    }


}
