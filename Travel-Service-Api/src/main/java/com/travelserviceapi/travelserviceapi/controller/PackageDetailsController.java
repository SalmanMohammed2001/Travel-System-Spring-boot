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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/packageDetails")
@CrossOrigin
public class PackageDetailsController {

 private final PackageDetailsService packageDetailsService;

    public PackageDetailsController(PackageDetailsService packageDetailsService) {
        this.packageDetailsService = packageDetailsService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
//    @RequestParam boolean packageStatus,
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
        requestPackageDetailsDto.setPackageStatus(false);
        requestPackageDetailsDto.setHotel(hotel);

        requestPackageDetailsDto.setVehicle(vehicle);

        ResponsePackageDetailsDto save = packageDetailsService.save(requestPackageDetailsDto);
        return new ResponseEntity<>(
                new StandResponse(201, "save Package", save), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "{id}")
  //  @PreAuthorize("hasAuthority('package:read')")
    public ResponseEntity<StandResponse> findByNic(@PathVariable String id) throws IOException {
        ResponsePackageDetailsDto byId = packageDetailsService.findById(id);

        List<ResponsePackageDetailsDto> responsePackageDetailsDtos=new ArrayList<>();
        responsePackageDetailsDtos.add(byId);
        return new ResponseEntity<>(
                new StandResponse(201, "driver data",responsePackageDetailsDtos), HttpStatus.CREATED
        );
    }


    @PutMapping
    @PreAuthorize("hasAuthority('package:write')")
    public ResponseEntity<StandResponse> update(


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
            @RequestParam boolean packageStatus,
            @RequestParam String hotel,
            @RequestParam String vehicle,
            @RequestParam String packageId



    ) throws IOException {
        TravelDuration travelDuration = new TravelDuration(startDate,endDate,days,night);
        RequestPackageDetailsDto requestPackageDetailsDto = new RequestPackageDetailsDto();
        requestPackageDetailsDto.setPackageId(packageId);
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

        ResponsePackageDetailsDto save = packageDetailsService.update(requestPackageDetailsDto);
        return new ResponseEntity<>(
                new StandResponse(201, "update Package", save), HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = {"packageId"})
  //  @PreAuthorize("hasAuthority('package:write')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<StandResponse> delete(@RequestParam String packageId){
         packageDetailsService.delete(packageId);
        return new ResponseEntity<>(
                new StandResponse(201, "delete package",null), HttpStatus.CREATED
        );
    }

    @GetMapping()
  //  @PreAuthorize("hasAuthority('package:read')")
    public ResponseEntity<StandResponse> findAll() throws IOException {
        List<ResponsePackageDetailsDto> all = packageDetailsService.findAll();
        return new ResponseEntity<>(
                new StandResponse(201, "all package",all), HttpStatus.CREATED
        );
    }

    @GetMapping("count")
    //  @PreAuthorize("hasAuthority('package:read')")
    public ResponseEntity<StandResponse> findAllCount() throws IOException {
        long allPackageCount = packageDetailsService.findAllPackageCount();

        return new ResponseEntity<>(
                new StandResponse(201, "all package",allPackageCount), HttpStatus.CREATED
        );
    }
}
