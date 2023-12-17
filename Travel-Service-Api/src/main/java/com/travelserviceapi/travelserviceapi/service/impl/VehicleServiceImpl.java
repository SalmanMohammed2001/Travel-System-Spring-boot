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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void update(RequestVehicleDto dto) {
        if(vehicleRepo.existsById(dto.getVehicleId())){
            VehicleDto vehicleDto = mapper.map(dto, VehicleDto.class);
            Vehicle vehicle = mapper.map(dto, Vehicle.class);
            Optional<Vehicle> byId = vehicleRepo.findById(vehicleDto.getVehicleId());
            if(byId.isPresent()){
                deleteImage(byId);
                exPortImage(vehicleDto,vehicle);
                vehicleRepo.save(vehicle);
            }

        }else {
            throw new EntryNotFoundException("Vehicle Id Not Found");
        }
    }

    @Override
    public List<ResponseVehicleDto> findAll() throws IOException {
        List<Vehicle> all = vehicleRepo.findAll();
        List<ResponseVehicleDto> responseVehicleDtos = mapper.map(all, new TypeToken<List<ResponseVehicleDto>>() {}.getType());
        List<ResponseVehicleDto> responseVehicleDtos1 = importImagesAll(responseVehicleDtos, all);
        return responseVehicleDtos1;

    }


    public void deleteImage(Optional<Vehicle> byId){
        if (byId.isPresent()){
            Vehicle vehicle = byId.get();
            String images = vehicle.getVehicleImages();
            if (images != null){
                ArrayList<String> pathList = gson.fromJson(images, new TypeToken<ArrayList<String>>(){}.getType());
                for (String path : pathList) {
                    File file = new File(path);
                    boolean delete = file.delete();
                    System.out.println("Images " + delete);
                }
            }
        }
    }


    public void exPortImage(VehicleDto dto,Vehicle vehicle){
        ArrayList<byte[]> images = dto.getVehicleImages();
       String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");
   //     String dt = "img";

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

    public List<ResponseVehicleDto> importImagesAll(List<ResponseVehicleDto> vehicleDto,List<Vehicle>vehicles) throws IOException {
        Vehicle vehicle = new Vehicle();

        vehicles.forEach(data->{
            vehicle.setVehicleImages(data.getVehicleImages());
        });
        String images = vehicle.getVehicleImages();

        ResponseVehicleDto responseVehicleDto = new ResponseVehicleDto();
        responseVehicleDto.setVehicleImages(new ArrayList<>());

        ArrayList<String> imageList = gson.fromJson(images, new TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            responseVehicleDto.getVehicleImages().add(imgData);
        };

        List<ResponseVehicleDto> arrayList = new ArrayList<>();
        for (ResponseVehicleDto data:vehicleDto){
            arrayList.add(new ResponseVehicleDto(data.getVehicleId(),data.getVehicleName(),data.getVehiclePriceFor1Km(),
                    data.getVehicleCategory(),data.getVehicleType(),data.getVehiclePriceFor100Km(),data.getVehicleFuelType(),
                    data.getVehicleSeatCapacity(),data.getVehicleFuelUsage(),data.getVehicleHybrid(),data.getVehicleTransmission(),
                    responseVehicleDto.getVehicleImages(),responseVehicleDto.getVehicleQty()));
        }
        return arrayList;
    }


}
