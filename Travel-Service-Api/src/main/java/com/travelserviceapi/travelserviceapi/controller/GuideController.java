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
import java.util.List;

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
               "",
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

    @GetMapping(path = "{id}")
    public ResponseEntity<StandResponse> findById(@PathVariable String id) throws IOException {
        ResponseGuideDto byNic = guideService.findId(id);
        return new ResponseEntity<>(
                new StandResponse(201, "driver data", byNic), HttpStatus.CREATED
        );
    }


    @PutMapping
    public ResponseEntity<StandResponse> update(
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
            @RequestParam boolean guideStatus,
            @RequestPart String guideId

    ) throws IOException {

        RequestGuideDto requestGuideDto = new RequestGuideDto(
                guideId,
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




        guideService.update(requestGuideDto);
        return new ResponseEntity<>(
                new StandResponse(201, "drive update", null), HttpStatus.CREATED
        );
    }

    @DeleteMapping(params = {"guideId"})
    public ResponseEntity<StandResponse> delete(@RequestParam String guideId) {
        guideService.delete(guideId);
        return new ResponseEntity<>(
                new StandResponse(201, "drive update", null), HttpStatus.CREATED
        );
    }

    @GetMapping()
    public ResponseEntity<StandResponse> findAll() {
        List<ResponseGuideDto> all = guideService.findAll();
        return new ResponseEntity<>(
                new StandResponse(201, "drive all data", all), HttpStatus.CREATED
        );
    }

}
