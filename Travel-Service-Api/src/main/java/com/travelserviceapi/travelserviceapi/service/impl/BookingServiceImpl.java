package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.core.BookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.core.BookingDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.*;
import com.travelserviceapi.travelserviceapi.entity.*;
import com.travelserviceapi.travelserviceapi.exception.EntryNotFoundException;
import com.travelserviceapi.travelserviceapi.repo.*;
import com.travelserviceapi.travelserviceapi.service.BookingService;
import com.travelserviceapi.travelserviceapi.service.process.impl.EmailService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {



    private final BookingRepo bookingRepo;

    private final PackageDetailsRepo packageDetailsRepo;

    private final BookingDetailsRepo bookingDetailsRepo;


    private final UserRepo userRepo;

    private final Generator generator;

    private final Gson gson;

    private final ModelMapper mapper;

    private final EmailService emailService;


    @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo, PackageDetailsRepo packageDetailsRepo, BookingDetailsRepo bookingDetailsRepo, UserRepo userRepo, Generator generator, Gson gson, ModelMapper mapper, EmailService emailService) {
        this.bookingRepo = bookingRepo;
        this.packageDetailsRepo = packageDetailsRepo;
        this.bookingDetailsRepo = bookingDetailsRepo;
        this.userRepo = userRepo;
        this.generator = generator;
        this.gson = gson;
        this.mapper = mapper;
        this.emailService = emailService;
    }


    @Override
    public void saveBooking(RequestBookingDto dto) throws IOException {
        String generateKey = generator.generateKey("Next");
        BookingDto bookingDto = mapper.map(dto, BookingDto.class);
        bookingDto.setBookingId(generateKey);

        System.out.println(bookingDto.getBookingId());
        System.out.println(bookingDto.getBankSlip());
        System.out.println(bookingDto.getBookingDate());
        System.out.println(bookingDto.getBookingPrice());
        System.out.println(bookingDto.isBookingStatus());
        System.out.println(bookingDto.getUser());
        System.out.println(bookingDto.getBookingDetailsLis());


    //   if(emailService.createEmails())

      User user = userRepo.findById(bookingDto.getUser()).get();

       List<BookingDetails> bookingDetails=new ArrayList<>();
       bookingDto.getBookingDetailsLis().forEach(data->{
           if(emailService.createEmails(user.getUserEmail(),
                   "Next Travel Booking Details",
                   "<h1> Booking Code="+bookingDto.getBookingId()+"</h1> <br>" +
                           "<h1> Package Value="+bookingDto.getBookingPrice()+"</h1> <br>" +
                           "<h1> package Start Date="+data.getPackageStartDate()+"</h1> <br>" +
                           "<h1> package Start Date="+data.getPackageEndDate()+"</h1>"


                   )){
               bookingDetails.add(new BookingDetails(
                       bookingDto.getBookingId(),
                       data.getPackageId(),
                       data.getPackageCategory(),
                       data.getPackageStartDate(),
                       data.getPackageEndDate(),
                       data.getPackageValue(),
                       user.getUserEmail(),
                       null,
                       null
               ));


               PackageDetails packageDetails = packageDetailsRepo.findById(data.getPackageId()).get();
               //   packageDetails.setPackageStatus(true);

           }

       });

       Booking booking = new Booking(
                bookingDto.getBookingId(),
                bookingDto.getBookingDate(),
                bookingDto.getBookingPrice(),
                null,
                bookingDto.isBookingStatus(),
                user,
                bookingDetails);
        //   Booking booking = mapper.map(bookingDto, Booking.class);


       booking.setUser(user);
        exportImages(bookingDto, booking);



        booking.setBookingDetailsLis(bookingDetails);
       bookingRepo.save(booking);
    }

    @Override
    public void updateBooking(RequestBookingDto dto) throws IOException {
        if(bookingRepo.existsById(dto.getBookingId())){
            BookingDto bookingDto = mapper.map(dto, BookingDto.class);
            User user = userRepo.findByUserEmail(bookingDto.getUser());
            List<BookingDetails> bookingDetails=new ArrayList<>();


            bookingDto.getBookingDetailsLis().forEach(data->{
               PackageDetails packageDetailsID= packageDetailsRepo.findById(data.getPackageId()).get();
                Optional<BookingDetails> bookingDetailsRepoById = bookingDetailsRepo.findById(new BookingDetails_Pk(data.getBookingId(), data.getPackageId()));


                bookingDetails.add(new BookingDetails(
                        bookingDto.getBookingId(),
                        data.getPackageId(),
                        data.getPackageCategory(),
                        data.getPackageStartDate(),
                        data.getPackageEndDate(),
                        data.getPackageValue(),
                        user.getUsername(),
                        null,
                        null
                ));

                PackageDetails packageDetails = packageDetailsRepo.findById(data.getPackageId()).get();
                //   packageDetails.setPackageStatus(true);
            });


            Booking booking = new Booking(
                    bookingDto.getBookingId(),
                    bookingDto.getBookingDate(),
                    bookingDto.getBookingPrice(),
                    null,
                    bookingDto.isBookingStatus(),
                    user,
                    bookingDetails);

            bookingRepo.deleteById(dto.getBookingId());
            booking.setUser(user);
            exportImages(bookingDto, booking);
            booking.setBookingDetailsLis(bookingDetails);
            bookingRepo.save(booking);

        }else {
            throw new EntryNotFoundException("Id Not Found");
        }
    }


    @Override
    public List<ResponseBookingDto> findAll() throws IOException {
        List<Booking> all = bookingRepo.findAll();

        List<ResponseBookingDto> list=new ArrayList<>();

      List<ResponseBookingDetailsDto> responseBookingDtos;

        for(Booking b:all){

         responseBookingDtos=   mapper.map(b.getBookingDetailsLis(),new TypeToken<List<ResponseBookingDetailsDto>>(){}.getType());

            list.add(new ResponseBookingDto(
                    b.getBookingId(),b.getBookingDate(),b.getBookingPrice(),null,b.isBookingStatus()
                    ,b.getUser().getUserEmail(),responseBookingDtos
            ));
        }
  //    List<ResponseBookingDto> responseBookingDtos=  mapper.map(all,new TypeToken<List<ResponseBookingDto>>(){}.getType());
       List<ResponseBookingDto> responseBookingDtos1 = importImagesAll(list, all);
      /* responseBookingDtos1.forEach(data->{
           System.out.println(data.toString());
       });*/
        return responseBookingDtos1;
    }

    @Override
    public ResponseBookingDto findId(String id) {
       // bookingDetailsRepo.f
          Booking booking = bookingRepo.findById(id).get();
      List<ResponseBookingDetailsDto> responseBookingDetailsDto=  mapper.map(booking.getBookingDetailsLis(), new TypeToken<List<ResponseBookingDetailsDto>>() {}.getType());
        ResponseBookingDto responseBookingDto = new ResponseBookingDto(
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getBookingPrice(),
                null,
                booking.isBookingStatus(),
                booking.getUser().getUserEmail(),
                responseBookingDetailsDto
        );
        return  responseBookingDto;
    }

    @Override
    public void delete(String id) {
        if(bookingRepo.existsById(id)){
            bookingRepo.deleteById(id);
        }else {
            throw new EntryNotFoundException("Id not found");
        }
    }

    @Override
    public ResponseUpdateBookingDto findUpdateId(String id) throws IOException {
        if(bookingRepo.existsById(id)){
            Booking booking = bookingRepo.findById(id).get();
            ResponseUserDto responseUserDto = mapper.map(booking.getUser(), ResponseUserDto.class);

            List<ResponseBookingDetailsDto> responseBookingDetailsDto=  mapper.map(booking.getBookingDetailsLis(), new TypeToken<List<ResponseBookingDetailsDto>>() {}.getType());

            ResponseUpdateBookingDto responseUpdateBookingDto = new ResponseUpdateBookingDto(
                    booking.getBookingId(),
                    booking.getBookingDate(),
                    booking.getBookingPrice(),
                    null,
                    booking.isBookingStatus(),
                    responseUserDto,
                    responseBookingDetailsDto
            );

            importImages(responseUpdateBookingDto,booking);
            return  responseUpdateBookingDto;
        }else {
            throw new EntryNotFoundException("Id not found");
        }
    }

    @Override
    public long findAllBookingCount() {
      return  bookingRepo.findAllBookingCount();
    }

    @Override
    public List<ResponseBookingDto> findBookingByUserId(String userId) throws IOException {
        List<Booking> all = bookingRepo.findBookingByUserId(userId);


        List<ResponseBookingDto> list=new ArrayList<>();

        List<ResponseBookingDetailsDto> responseBookingDtos;

        for(Booking b:all){

            responseBookingDtos=   mapper.map(b.getBookingDetailsLis(),new TypeToken<List<ResponseBookingDetailsDto>>(){}.getType());

            list.add(new ResponseBookingDto(
                    b.getBookingId(),b.getBookingDate(),b.getBookingPrice(),null,b.isBookingStatus()
                    ,b.getUser().getUserEmail(),responseBookingDtos
            ));
        }

        List<ResponseBookingDto> responseBookingDtos1 = importImagesAll(list, all);
        return responseBookingDtos1;

    }


    public void importImages(ResponseUpdateBookingDto responseUpdateBookingDto,Booking booking) throws IOException {

        BufferedImage read = ImageIO.read(new File(booking.getBankSlip()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        byte[] bytes = baos.toByteArray();
     //   dto.setProfilePic(Base64.getEncoder().encodeToString(bytes));
        responseUpdateBookingDto.setBankSlip(bytes);

    }
    public void exportImages(BookingDto dto, Booking booking) throws IOException {
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");

        if (dto.getBankSlip() != null) {
            InputStream is = new ByteArrayInputStream(dto.getBankSlip());
            BufferedImage bi = ImageIO.read(is);
            File outputfile = new File("images/booking/" + dt + ".jpg");
            ImageIO.write(bi, "jpg", outputfile);
            booking.setBankSlip(outputfile.getAbsolutePath());

        }


    }




    public List<ResponseBookingDto> importImagesAll(List<ResponseBookingDto> dto1, List<Booking> bookings) throws IOException {
        Booking booking = new Booking();
        bookings.forEach(data -> {
           booking.setBankSlip(data.getBankSlip());
        });

      ResponseBookingDto dto = new ResponseBookingDto();
        BufferedImage read = ImageIO.read(new File(booking.getBankSlip()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        // dto.setG(Base64.getEncoder().encodeToString(bytes));
        dto.setBankSlip(bytes);

        ArrayList<ResponseBookingDto> list = new ArrayList<>();
        for (ResponseBookingDto data : dto1) {
            list.add(new ResponseBookingDto(
                    data.getBookingId(),data.getBookingDate(),data.getBookingPrice(),dto.getBankSlip(),
                    data.isBookingStatus(),data.getUser(),data.getBookingDetailsLis()
            ));
        }
        return  list;


    }
}
