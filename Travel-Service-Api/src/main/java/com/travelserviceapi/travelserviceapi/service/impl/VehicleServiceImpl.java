package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.travelserviceapi.travelserviceapi.dto.core.VehicleDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.exception.EntryNotFoundException;
import com.travelserviceapi.travelserviceapi.repo.VehicleRepo;
import com.travelserviceapi.travelserviceapi.service.VehicleService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public ResponseVehicleDto save(RequestVehicleDto dto) throws IOException {
        String primaryKey = generator.generateKey("Vehicle");
        VehicleDto vehicleDto = mapper.map(dto, VehicleDto.class);
        vehicleDto.setVehicleId(primaryKey);
        if(!vehicleRepo.existsById(vehicleDto.getVehicleId())){
            Vehicle vehicle = mapper.map(vehicleDto, Vehicle.class);
            exPortImage(vehicleDto,vehicle);
            Vehicle save = vehicleRepo.save(vehicle);
            ResponseVehicleDto responseVehicleDto = mapper.map(vehicle, ResponseVehicleDto.class);
            importImages(responseVehicleDto,save);
            return responseVehicleDto;
        }else {
            throw new DuplicateEntryException("Vehicle Id Duplicate");
        }
    }

    @Override
    public ResponseVehicleDto findById(String id) throws IOException {
        if(vehicleRepo.existsById(id)){
            Vehicle vehicle = vehicleRepo.findById(id).get();
            ResponseVehicleDto responseVehicleDto = mapper.map(vehicle, ResponseVehicleDto.class);
            importImages(responseVehicleDto,vehicle);
            return responseVehicleDto;
        }else {
            throw new EntryNotFoundException("Id Not Found");
        }
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
                    File outputfile = new File("images/vehicle/" + dt + "_" + i + ".jpg");
                    ImageIO.write(bi, "jpg", outputfile);
                    pathList.add(outputfile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        vehicle.setVehicleImages(gson.toJson(pathList));


    }

    public void importImages(ResponseVehicleDto vehicleDto,Vehicle vehicle) throws IOException {
        String images = vehicle.getVehicleImages();
        vehicleDto.setVehicleImages(new ArrayList<>());
        ArrayList<String> imageList = gson.fromJson(images, new TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            vehicleDto.getVehicleImages().add(imgData);
        }
    }

}
