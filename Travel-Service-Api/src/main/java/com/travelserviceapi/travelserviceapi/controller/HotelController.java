package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestGuideDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestHotelDto;
import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.embadded.Price;
import com.travelserviceapi.travelserviceapi.service.HotelService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    private String hotelName;

    @PostMapping
    public ResponseEntity<StandResponse> save(

            @RequestParam String hotelCategory,
            @RequestParam String hotelName,
            @RequestParam String hotelPetAllowed,
            @RequestParam String hotelMapLink,
            @RequestParam String hotelAddress,
            @RequestParam String contact1,
            @RequestParam String contact2,
            @RequestParam String hotelEmail,
       @RequestParam List<MultipartFile> images,
          @RequestParam boolean hotelStatus,
            @RequestParam String option1,
            @RequestParam String option2,
            @RequestParam String option3,
            @RequestParam String price1,
            @RequestParam String price2,
            @RequestParam String price3

    ) throws IOException {
       ArrayList<byte[]> bytes = new ArrayList<>();
        images.forEach(data->{
            try {
                bytes.add(data.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Price p1 = new Price(option1, price1);
        Price p2 = new Price(option2, price2);
        Price p3 = new Price(option3, price3);

        Contact contact = new Contact(contact1,contact2);

        RequestHotelDto requestHotelDto = new RequestHotelDto();
        requestHotelDto.setHotelCategory(hotelCategory);
        requestHotelDto.setHotelName(hotelName);
        requestHotelDto.setHotelAddress(hotelAddress);
        requestHotelDto.setHotelMapLink(hotelMapLink);
        requestHotelDto.setHotelPetAllowed(hotelPetAllowed);
        requestHotelDto.setHotelContact(contact);
        requestHotelDto.setHotelEmail(hotelEmail);
        requestHotelDto.setHotelPrices(Arrays.asList(p1,p2,p3));
        requestHotelDto.setImages(bytes);
        requestHotelDto.setHotelStatus(hotelStatus);
        hotelService.save(requestHotelDto);
        return new ResponseEntity<>(
                new StandResponse(201, "hotel saved", null), HttpStatus.CREATED
        );
    }

  /*  @GetMapping(path = "{id}")
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
*/
}
