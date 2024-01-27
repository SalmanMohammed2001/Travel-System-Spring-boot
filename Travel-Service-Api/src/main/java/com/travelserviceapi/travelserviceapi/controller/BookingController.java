package com.travelserviceapi.travelserviceapi.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelserviceapi.travelserviceapi.dto.core.PricesDTO;
import com.travelserviceapi.travelserviceapi.dto.core.SampleDto;
import com.travelserviceapi.travelserviceapi.dto.core.VehicleDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseBookingDto;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.service.BookingService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import lombok.Getter;
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
@CrossOrigin
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
    //@RequestParam boolean bookingStatus,
    @RequestParam String user,
    @RequestParam ArrayList<RequestBookingDetailsDto> bookingDetails
//  @RequestParam ArrayList<RequestBookingDetailsDto> data
    /*@RequestParam String bookingId,
    @RequestParam String packageId,
    @RequestParam String date,
    @RequestParam double total,
    @RequestParam String guideName,
    @RequestParam String vehicleType*/
    ) throws IOException {



        List<RequestBookingDetailsDto> requestBookingDetailsDtos=new ArrayList<>();
        bookingDetails.forEach(data->{
            requestBookingDetailsDtos.add(new RequestBookingDetailsDto(
                    data.getPackageId(),data.getPackageCategory(),
                    data.getPackageStartDate(),data.getPackageEndDate(),data.getPackageValue()
            ));
        });

      RequestBookingDto requestBookingDto = new RequestBookingDto();
        requestBookingDto.setBookingDate(bookingDate);
        requestBookingDto.setBookingPrice(bookingPrice);
        requestBookingDto.setBankSlip(bankSlip);
        requestBookingDto.setBookingStatus(true);
        requestBookingDto.setUser(user);
        requestBookingDto.setBookingDetailsLis(requestBookingDetailsDtos);
        bookingService.saveBooking(requestBookingDto);
        return new ResponseEntity<>(
                new StandResponse(201, "booking saved", null), HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<StandResponse> findAll() throws IOException {
        List<ResponseBookingDto> all = bookingService.findAll();
        return new ResponseEntity<>(
                new StandResponse(201, "booking all", all), HttpStatus.CREATED
        );

    }

}
