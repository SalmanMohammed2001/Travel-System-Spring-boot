package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.travelserviceapi.travelserviceapi.dto.core.DriverDto;
import com.travelserviceapi.travelserviceapi.dto.core.UserDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.entity.Driver;
import com.travelserviceapi.travelserviceapi.entity.User;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.exception.EntryNotFoundException;
import com.travelserviceapi.travelserviceapi.repo.DriverRepo;
import com.travelserviceapi.travelserviceapi.service.DriverService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
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
import java.util.Optional;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;

    private final ModelMapper mapper;

    private Generator generator;

    private Gson gson;

    @Autowired
    public DriverServiceImpl(DriverRepo driverRepo, ModelMapper mapper, Generator generator, Gson gson) {
        this.driverRepo = driverRepo;
        this.mapper = mapper;
        this.generator = generator;
        this.gson = gson;
    }

    @Override
    public ResponseDriverDto save(RequestDriverDto dto){

        try {
            String generateKey = generator.generateKey("Driver");
            DriverDto driverDto = mapper.map(dto, DriverDto.class);
            driverDto.setDriverId(generateKey);
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(driverDto.getVehicle());
            Driver  driver = mapper.map(driverDto, Driver.class);
            driver.setVehicle(vehicle);
            exportImages(driverDto,driver);
            Driver save = driverRepo.save(driver);
            ResponseVehicleDto responseVehicleDto = mapper.map(save.getVehicle(), ResponseVehicleDto.class);
            ResponseDriverDto responseDriverDto = mapper.map(save, ResponseDriverDto.class);
            responseDriverDto.setVehicle(responseVehicleDto);
           importImages(responseDriverDto,save);
            return responseDriverDto;
        }catch (IOException e){
            throw new DuplicateEntryException("id Duplicate");
        }


       // return null;
    }

    @Override
    public ResponseDriverDto findByNic(String nic) {
        try{
            if(driverRepo.existsByDriverNic(nic)){
                Driver byDriverNic = driverRepo.findByDriverNic(nic);

                Vehicle vehicle = mapper.map(byDriverNic.getVehicle(), Vehicle.class);
                System.out.println(vehicle.getVehicleImages());
                ResponseVehicleDto responseVehicleDto = mapper.map(byDriverNic.getVehicle(), ResponseVehicleDto.class);
                importImages(responseVehicleDto,vehicle);
                ResponseDriverDto responseDriverDto = mapper.map(byDriverNic, ResponseDriverDto.class);
                responseDriverDto.setVehicle(responseVehicleDto);
                importImages(responseDriverDto,byDriverNic);
                return  responseDriverDto;
            }else {
                throw new EntryNotFoundException("nic not found");
            }
        } catch (IOException e){
            throw new EntryNotFoundException("nic not found");
        }

    }

    @Override
    public void update(RequestDriverDto dto) {

        try {
            if(driverRepo.existsById(dto.getDriverId())){
                DriverDto driverDto = mapper.map(dto, DriverDto.class);
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(driverDto.getVehicle());
                Driver driver = mapper.map(driverDto, Driver.class);
                Driver byId = driverRepo.findById(driverDto.getDriverId()).get();
                deleteImages(driverDto,byId);
                driver.setVehicle(vehicle);
                exportImages(driverDto,driver);
                driverRepo.save(driver);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteDriver(String id) {
        if(driverRepo.existsById(id)){
            Driver driver = driverRepo.findById(id).get();
            deleteImagesById(driver);
            driverRepo.deleteById(id);

        }else {
            throw new EntryNotFoundException("id not found");
        }
    }


    public void exportImages(DriverDto dto, Driver driver) throws IOException {
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");

        if (dto.getDriverImage() != null) {
            InputStream is = new ByteArrayInputStream(dto.getDriverImage());
            BufferedImage bi = ImageIO.read(is);
            File outputfile = new File("images/driver/pic/" + dt + ".jpg");
            ImageIO.write(bi, "jpg", outputfile);
            driver.setDriverImage(outputfile.getAbsolutePath());
        }

        if (dto.getLicenseImageFront() != null) {
            InputStream is1 = new ByteArrayInputStream(dto.getLicenseImageFront());
            BufferedImage bi1 = ImageIO.read(is1);
            File outputfile1 = new File("images/driver/nic_front/" + dt + ".jpg");
            ImageIO.write(bi1, "jpg", outputfile1);
            driver.setLicenseImageFront(outputfile1.getAbsolutePath());
        }

        if (dto.getLicenseImageRear() != null) {
            InputStream is2 = new ByteArrayInputStream(dto.getLicenseImageRear());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile2 = new File("images/driver/nic_rear/" + dt + ".jpg");
            ImageIO.write(bi2, "jpg", outputfile2);
            driver.setLicenseImageRear(outputfile2.getAbsolutePath());
        }

    }
    public void importImages(ResponseDriverDto dto, Driver driver) throws IOException {

        BufferedImage read = ImageIO.read(new File(driver.getDriverImage()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        byte[] bytes = baos.toByteArray();
       // dto.setProfilePic(Base64.getEncoder().encodeToString(bytes));
        dto.setDriverImage(bytes);

        read = ImageIO.read(new File(driver.getLicenseImageFront()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
     //   dto.setNicFrontImg(Base64.getEncoder().encodeToString(bytes));
        dto.setLicenseImageFront(bytes);

        read = ImageIO.read(new File(driver.getLicenseImageRear()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
     //   dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setLicenseImageRear(bytes);
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

    private void deleteImages(DriverDto driverDto, Driver driver) {
        if (driverDto.getDriverImage()!=null){
            System.out.println("not null");
            boolean delete = new File(driver.getDriverImage()).delete();
        }
        if (driverDto.getLicenseImageFront()!=null){
            System.out.println("not null");
            boolean delete = new File(driver.getLicenseImageFront()).delete();
        }
        if (driverDto.getLicenseImageRear()!=null){
            System.out.println("not null");
            boolean delete = new File(driver.getLicenseImageRear()).delete();
        }
    }

    private void deleteImagesById(Driver driver) {

        boolean delete1 = new File(driver.getDriverImage()).delete();

        boolean delete2 = new File(driver.getLicenseImageFront()).delete();

        boolean delete3 = new File(driver.getLicenseImageRear()).delete();
    }

}
