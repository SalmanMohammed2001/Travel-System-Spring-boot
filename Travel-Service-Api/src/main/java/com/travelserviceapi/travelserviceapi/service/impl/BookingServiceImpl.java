package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.dto.core.BookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.core.BookingDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseGuideDto;
import com.travelserviceapi.travelserviceapi.entity.*;
import com.travelserviceapi.travelserviceapi.repo.*;
import com.travelserviceapi.travelserviceapi.service.BookingService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {


    private final BookingRepo bookingRepo;

    private final PackageDetailsRepo packageDetailsRepo;

    private final BookingDetailsRepo bookingDetailsRepo;
    private final GuideRepo guideRepo;

    private final UserRepo userRepo;

    private final Generator generator;

    private final ModelMapper mapper;

    public BookingServiceImpl(BookingRepo bookingRepo, PackageDetailsRepo packageDetailsRepo, BookingDetailsRepo bookingDetailsRepo, GuideRepo guideRepo, UserRepo userRepo, Generator generator, ModelMapper mapper) {
        this.bookingRepo = bookingRepo;
        this.packageDetailsRepo = packageDetailsRepo;
        this.bookingDetailsRepo = bookingDetailsRepo;
        this.guideRepo = guideRepo;
        this.userRepo = userRepo;
        this.generator = generator;
        this.mapper = mapper;
    }


    @Override
    public void saveBooking(RequestBookingDto dto) throws IOException {
        String generateKey = generator.generateKey("Next");
        BookingDto bookingDto = mapper.map(dto, BookingDto.class);
        bookingDto.setBookingId(generateKey);


        Guide guide = guideRepo.findById(bookingDto.getGuide()).get();
        guide.setGuideStatus(true);
        guideRepo.save(guide);
        User user = userRepo.findById(bookingDto.getUser()).get();

        Booking booking = mapper.map(bookingDto, Booking.class);
        booking.setUser(user);
        booking.setGuide(guide);
        exportImages(bookingDto, booking);



        List<BookingDetailsDto> bookingDetailsDto = new ArrayList<>();
        for (RequestBookingDetailsDto dto1 : dto.getBookingDetailsLis()) {
            bookingDetailsDto.add(new BookingDetailsDto(bookingDto.getBookingId(), dto1.getPackageId(), dto1.getDate(), dto1.getTotal(), guide.getGuideName(), user.getUsername()));
            PackageDetails packageDetails = packageDetailsRepo.findById(dto1.getPackageId()).get();
            packageDetails.setPackageStatus(true);
            System.out.println(user.getUsername());
            System.out.println(guide.getGuideName());

        }
        List<BookingDetails> bookingDetails = mapper.map(bookingDetailsDto, new TypeToken<List<BookingDetails>>() {}.getType());

        booking.setBookingDetailsLis(bookingDetails);
       bookingRepo.save(booking);
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

    public void importImages(ResponseGuideDto dto, Guide guide) throws IOException {

        BufferedImage read = ImageIO.read(new File(guide.getGuideProfilePicImage()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        // dto.setG(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideProfilePicImage(bytes);


        read = ImageIO.read(new File(guide.getGuideNicFrontImag()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        // dto.setNicFrontImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideNicFrontImag(bytes);

        read = ImageIO.read(new File(guide.getGuideNicRearImage()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        // dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideNicRearImage(bytes);

        read = ImageIO.read(new File(guide.getGuideIdFrontImage()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        // dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideIdFrontImage(bytes);


        read = ImageIO.read(new File(guide.getGuideIdRearImage()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        // dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideIdRearImage(bytes);
    }
}
