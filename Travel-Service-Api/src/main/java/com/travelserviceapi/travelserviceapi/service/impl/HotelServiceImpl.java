package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.core.HotelDto;
import com.travelserviceapi.travelserviceapi.dto.core.VehicleDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestHotelDto;
import com.travelserviceapi.travelserviceapi.entity.Hotel;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.repo.HotelRepo;
import com.travelserviceapi.travelserviceapi.service.HotelService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private final ModelMapper mapper;

    private final Generator generator;
    private final HotelRepo hotelRepo;

    private final Gson gson;

    public HotelServiceImpl(ModelMapper mapper, Generator generator, HotelRepo hotelRepo, Gson gson) {
        this.mapper = mapper;
        this.generator = generator;
        this.hotelRepo = hotelRepo;
        this.gson = gson;
    }

    @Override
    public void save(RequestHotelDto dto) {
        String generateKey = generator.generateKey("Hotel");
        HotelDto hotelDto = mapper.map(dto, HotelDto.class);
        hotelDto.setHotelId(generateKey);
        if(!hotelRepo.existsById(hotelDto.getHotelId())){
            Hotel hotel = mapper.map(hotelDto, Hotel.class);
            exPortImage(hotelDto,hotel);
            hotelRepo.save(hotel);

        }else {
            throw new DuplicateEntryException("Duplicate Id");
        }
    }

    public void exPortImage(HotelDto dto, Hotel hotel){
        ArrayList<byte[]> images = dto.getImages();
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");
        //     String dt = "img";

        ArrayList<String> pathList = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            {
                try {
                    InputStream is = new ByteArrayInputStream(images.get(i));
                    BufferedImage bi = ImageIO.read(is);
                    File outputfile = new File("images/hotel/" + dt + "_" + i + ".jpg");
                    ImageIO.write(bi, "jpg", outputfile);
                    pathList.add(outputfile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        hotel.setImages(gson.toJson(pathList));


    }
}
