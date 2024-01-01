package com.travelserviceapi.travelserviceapi.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelserviceapi.travelserviceapi.dto.core.PricesDTO;
import com.travelserviceapi.travelserviceapi.dto.core.SampleDto;
import com.travelserviceapi.travelserviceapi.dto.core.VehicleDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.service.BookingService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import org.mapstruct.ap.shaded.freemarker.template.SimpleDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping
    public ResponseEntity<StandResponse> save(
    @RequestParam String bookingDate,
    @RequestParam double bookingPrice,
    @RequestPart byte[] bankSlip,
    @RequestParam boolean bookingStatus,
    @RequestParam String guide,
    @RequestParam String user,
    @RequestParam ArrayList<RequestBookingDetailsDto> dto
//  @RequestParam ArrayList<RequestBookingDetailsDto> data
    /*@RequestParam String bookingId,
    @RequestParam String packageId,
    @RequestParam String date,
    @RequestParam double total,
    @RequestParam String guideName,
    @RequestParam String vehicleType*/
    ) throws IOException {





        RequestBookingDto requestBookingDto = new RequestBookingDto();
        requestBookingDto.setBookingDate(bookingDate);
        requestBookingDto.setBookingPrice(bookingPrice);
        requestBookingDto.setBankSlip(bankSlip);
        requestBookingDto.setBookingStatus(bookingStatus);
        requestBookingDto.setGuide(guide);
        requestBookingDto.setUser(user);
        requestBookingDto.setBookingDetailsLis(dto);
        bookingService.saveBooking(requestBookingDto);
        return new ResponseEntity<>(
                new StandResponse(201, "booking saved", null), HttpStatus.CREATED
        );
    }

 /*   @PostMapping()
    public ResponseEntity<StandResponse> save(@RequestParam String name, @RequestParam SampleDto dto){

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleId(dto.getId());
        System.out.println(vehicleDto.getVehicleId());

        return new ResponseEntity<>(
                new StandResponse(201, "booking saved", null), HttpStatus.CREATED
        );

    }*/

}
