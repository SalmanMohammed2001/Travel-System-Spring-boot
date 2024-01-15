package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.service.VehicleService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@CrossOrigin
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
            @RequestParam String vehicleFuelUsage,
            @RequestParam String vehicleHybrid,
            @RequestParam String vehicleTransmission,
            @RequestParam int vehicleQty,
            @RequestParam List<MultipartFile> vehicleImages
           // @RequestParam boolean vehicleState
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
                "",
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
                vehicleQty,
                false

        );

       ResponseVehicleDto save = vehicleService.save(requestVehicleDto);
        System.out.println(requestVehicleDto.getVehicleImages());
        return new ResponseEntity<>(
                new StandResponse(201, "Customer saved",save ), HttpStatus.CREATED
        );
    }


    @GetMapping(path = "{id}")
    public List<ResponseVehicleDto> findByID(@PathVariable String id) throws IOException {
        ResponseVehicleDto byId = vehicleService.findById(id);

        List<ResponseVehicleDto> responseVehicleDtos = new ArrayList<>();
        responseVehicleDtos.add(new ResponseVehicleDto(
                byId.getVehicleId()
                , byId.getVehicleName(),
                byId.getVehiclePriceFor1Km(),
                byId.getVehicleCategory(),
                byId.getVehicleType(),
                byId.getVehiclePriceFor100Km(),
                byId.getVehicleFuelType(),
                byId.getVehicleSeatCapacity(),
                byId.getVehicleFuelUsage(),
                byId.getVehicleHybrid(),
                byId.getVehicleTransmission()
                ,byId.getVehicleImages(),
                byId.getVehicleQty(),
                byId.isVehicleState()

        ));

       return responseVehicleDtos;
       /* return new ResponseEntity<>(
                new StandResponse(201, "Customer find", byId), HttpStatus.CREATED
        );*/
    }

    @PutMapping
    public ResponseEntity<StandResponse> update(
            @RequestParam String vehicleId,
            @RequestParam String vehicleName,
            @RequestParam double vehiclePriceFor1Km,
            @RequestParam String vehicleCategory,
            @RequestParam String vehicleType,
            @RequestParam double vehiclePriceFor100Km,
            @RequestParam String vehicleFuelType,
            @RequestParam int vehicleSeatCapacity,
            @RequestParam String vehicleFuelUsage,
            @RequestParam String vehicleHybrid,
            @RequestParam String vehicleTransmission,
            @RequestParam int vehicleQty,
            @RequestParam List<MultipartFile> vehicleImages
       //    @RequestParam  boolean vehicleState
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
                vehicleId,
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
                vehicleQty,
                false

        );

        vehicleService.update(requestVehicleDto);
        return new ResponseEntity<>(
                new StandResponse(201, "Vehicle update", null), HttpStatus.CREATED
        );
    }
    @GetMapping

    public ResponseEntity<StandResponse>findAll() throws IOException {
        List<ResponseVehicleDto> all = vehicleService.findAll();
        return new ResponseEntity<>(
                new StandResponse(201, "Vehicle update", all), HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = {"vehicleId"})
    public ResponseEntity<StandResponse>delete(@RequestParam String vehicleId) throws IOException {
                    vehicleService.deleteById(vehicleId);
        return new ResponseEntity<>(
                new StandResponse(201, "Vehicle delete", null), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "list")
    public ResponseEntity<StandResponse>findAllVehicleStateFalse() throws IOException {
        List<ResponseVehicleDto> all = vehicleService.findAllVehicleStatesFalse();
        return new ResponseEntity<>(
                new StandResponse(201, "Vehicle update", all), HttpStatus.CREATED
        );
    }


}
