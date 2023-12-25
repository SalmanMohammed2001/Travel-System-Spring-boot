package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestGuideDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseGuideDto;
import com.travelserviceapi.travelserviceapi.service.GuideService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/guide")
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @PostMapping
    public ResponseEntity<StandResponse> save(
            @RequestPart String guideName,
            @RequestPart String guideAddress,
            @RequestPart String guideContact,
            @RequestPart String guideBirthDate,
           @RequestParam double guideManDayValue,
            @RequestPart String guideExperience,
          @RequestPart byte[] guideIdFrontImage,
            @RequestPart byte[] guideIdRearImage,
            @RequestPart byte[] guideNicFrontImag,
            @RequestPart byte[] guideNicRearImage,
            @RequestPart byte[] guideProfilePicImage,
            @RequestParam boolean guideStatus


    ) throws IOException {

       RequestGuideDto requestGuideDto = new RequestGuideDto(
                guideName,
                guideAddress,
                guideContact,
                guideBirthDate,
                guideManDayValue,
               guideExperience,
                guideIdFrontImage,
                guideIdRearImage,
                guideNicFrontImag,
                guideNicRearImage,
                guideProfilePicImage,
                guideStatus
        );

         guideService.saveGuide(requestGuideDto);
        return new ResponseEntity<>(
                new StandResponse(201, "drive saved",   guideService.saveGuide(requestGuideDto)), HttpStatus.CREATED
        );
    }

  /*  @GetMapping(path = "{nic}")
    public ResponseEntity<StandResponse> findByNic(@PathVariable String nic) {
        ResponseDriverDto byNic = driverService.findByNic(nic);
        return new ResponseEntity<>(
                new StandResponse(201, "driver data", byNic), HttpStatus.CREATED
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

    @DeleteMapping(params = {"id"})
    public ResponseEntity<StandResponse> delete(@RequestParam String id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(
                new StandResponse(201, "drive update", null), HttpStatus.CREATED
        );
    }
*/
}
