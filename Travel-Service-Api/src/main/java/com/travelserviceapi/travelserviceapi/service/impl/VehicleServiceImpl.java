package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.core.VehicleDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.repo.VehicleRepo;
import com.travelserviceapi.travelserviceapi.service.VehicleService;
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
import java.util.ArrayList;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final ModelMapper mapper;
    private final VehicleRepo vehicleRepo;

    private final Gson gson;

    private Generator generator;

    public VehicleServiceImpl
            (ModelMapper mapper, VehicleRepo vehicleRepo, Gson gson, Generator generator) {
        this.mapper = mapper;
        this.vehicleRepo = vehicleRepo;
        this.gson = gson;
        this.generator = generator;
    }

    @Override
    public ResponseVehicleDto save(RequestVehicleDto dto) {
        String primaryKey = generator.generateKey("Vehicle-");
        VehicleDto vehicleDto = mapper.map(dto, VehicleDto.class);
        if(!vehicleRepo.existsById(vehicleDto.getVehicleId())){
            Vehicle vehicle = mapper.map(vehicleDto, Vehicle.class);
            exPortImage(vehicleDto,vehicle);
            vehicleRepo.save(vehicle);



        }else {
            throw new DuplicateEntryException("Vehicle Id Duplicate");
        }
        return null;
    }

    public void exPortImage(VehicleDto dto,Vehicle vehicle){
        ArrayList<byte[]> images = dto.getVehicleImages();
/*        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");*/
        String dt = "img";

        ArrayList<String> pathList = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            {
                try {
                    InputStream is = new ByteArrayInputStream(images.get(i));
                    BufferedImage bi = ImageIO.read(is);
                    File outputfile = new File("images/user/" + dt + "_" + i + ".jpg");
                    ImageIO.write(bi, "jpg", outputfile);
                    pathList.add(outputfile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        vehicle.setVehicleImages(gson.toJson(pathList));


    }

}
