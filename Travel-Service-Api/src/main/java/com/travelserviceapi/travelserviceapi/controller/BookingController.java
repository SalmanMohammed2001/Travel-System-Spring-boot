package com.travelserviceapi.travelserviceapi.controller;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseHotelDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUpdateBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseYearAndIncome;
import com.travelserviceapi.travelserviceapi.service.BookingService;
import com.travelserviceapi.travelserviceapi.util.StandResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
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
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<StandResponse> Update(
            @RequestParam String bookingId,
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
        requestBookingDto.setBookingId(bookingId);
        requestBookingDto.setBookingDate(bookingDate);
        requestBookingDto.setBookingPrice(bookingPrice);
        requestBookingDto.setBankSlip(bankSlip);
        requestBookingDto.setBookingStatus(true);
        requestBookingDto.setUser(user);
        requestBookingDto.setBookingDetailsLis(requestBookingDetailsDtos);

        System.out.println(requestBookingDto.getUser());
      bookingService.updateBooking(requestBookingDto);
        return new ResponseEntity<>(
                new StandResponse(201, "booking saved", null), HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<StandResponse> findAll() throws IOException {
        List<ResponseBookingDto> all = bookingService.findAll();
        return new ResponseEntity<>(
                new StandResponse(201, "booking all", all), HttpStatus.CREATED
        );

    }

    @DeleteMapping(params = {"id"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<StandResponse> delete(@RequestParam String id) throws IOException {
     bookingService.delete(id);
        return new ResponseEntity<>(
                new StandResponse(201, "delete", null), HttpStatus.CREATED
        );

    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<StandResponse> findId(@PathVariable String id ) throws IOException {
        ResponseBookingDto responseBookingDto = bookingService.findId(id);
        List<ResponseBookingDto>  responseBookingDtos=new ArrayList<>();
        responseBookingDtos.add(responseBookingDto);
        return new ResponseEntity<>(
                new StandResponse(201, "booking all", responseBookingDtos), HttpStatus.CREATED
        );

    }
    @GetMapping(path = "/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<StandResponse> findUpdateId(@PathVariable String id ) throws IOException {
        System.out.println(id);
        ResponseUpdateBookingDto responseUpdateBookingDto = bookingService.findUpdateId(id);

        List<ResponseUpdateBookingDto>  responseUpdateBookingDtos=new ArrayList<>();
        responseUpdateBookingDtos.add(responseUpdateBookingDto);

        return new ResponseEntity<>(
                new StandResponse(201, "booking all", responseUpdateBookingDtos), HttpStatus.CREATED
        );

    }

    @GetMapping(path = "year-income")
  //  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<StandResponse> YearlyIncome() throws IOException {
        ResponseYearAndIncome byYearlyIncome = bookingService.findByYearlyIncome();
        return new ResponseEntity<>(
                new StandResponse(201, "booking all",null), HttpStatus.CREATED
        );

    }

}
