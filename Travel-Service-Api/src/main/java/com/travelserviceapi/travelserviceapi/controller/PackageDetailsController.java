package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestPackageDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponsePackageDetailsDto;
import com.travelserviceapi.travelserviceapi.embadded.TravelDuration;
import com.travelserviceapi.travelserviceapi.entity.BookingDetails;
import com.travelserviceapi.travelserviceapi.service.PackageDetailsService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/packageDetails")
public class PackageDetailsController {

 private final PackageDetailsService packageDetailsService;

    public PackageDetailsController(PackageDetailsService packageDetailsService) {
        this.packageDetailsService = packageDetailsService;
    }

    @PostMapping
    public ResponseEntity<StandResponse> save(


    @RequestParam String category,
    @RequestParam String startDate,
    @RequestParam String endDate,
    @RequestParam int days,
    @RequestParam int night,
    @RequestParam String packageTravelArea,
    @RequestParam String packageNoAdult,
    @RequestParam String packageNoChildren,
    @RequestParam String packageTotalHeadCount,
    @RequestParam String withPetOrNo,
    @RequestParam double packageValue,
    @RequestParam String packageStatus,
    @RequestParam String hotel,
    @RequestParam String vehicle



    ) throws IOException {
        TravelDuration travelDuration = new TravelDuration(startDate,endDate,days,night);
        RequestPackageDetailsDto requestPackageDetailsDto = new RequestPackageDetailsDto();
        requestPackageDetailsDto.setCategory(category);
        requestPackageDetailsDto.setTravelDuration(travelDuration);
        requestPackageDetailsDto.setPackageTravelArea(packageTravelArea);
        requestPackageDetailsDto.setPackageNoAdult(packageNoAdult);
        requestPackageDetailsDto.setPackageNoChildren(packageNoChildren);
        requestPackageDetailsDto.setPackageTotalHeadCount(packageTotalHeadCount);
        requestPackageDetailsDto.setWithPetOrNo(withPetOrNo);
        requestPackageDetailsDto.setPackageValue(packageValue);
        requestPackageDetailsDto.setPackageStatus(packageStatus);
        requestPackageDetailsDto.setHotel(hotel);
        requestPackageDetailsDto.setVehicle(vehicle);

        ResponsePackageDetailsDto save = packageDetailsService.save(requestPackageDetailsDto);
        return new ResponseEntity<>(
                new StandResponse(201, "save Package", save), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<StandResponse> findByNic(@PathVariable String id){
        ResponsePackageDetailsDto byId = packageDetailsService.findById(id);
        return new ResponseEntity<>(
                new StandResponse(201, "driver data",byId ), HttpStatus.CREATED
        );
    }

 /*   private ResponsePackageDetailsDto find(@PathVariable String id){
        ResponsePackageDetailsDto byId = packageDetailsService.findById(id);
        return byId;
    }
*/


  /*  @PutMapping
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

    @DeleteMapping(params = {"id"})
    public ResponseEntity<StandResponse> delete(@RequestParam String id){
        driverService.deleteDriver(id);
        return new ResponseEntity<>(
                new StandResponse(201, "drive update", null), HttpStatus.CREATED
        );
    }*/

}
