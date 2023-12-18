package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.core.DriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.entity.Driver;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.repo.DriverRepo;
import com.travelserviceapi.travelserviceapi.service.DriverService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            DriverDto driverDto = mapper.map(dto, DriverDto.class);
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(driverDto.getVehicle());
            Driver  driver = mapper.map(driverDto, Driver.class);
            driver.setVehicle(vehicle);
            exportImages(driverDto,driver);
            driverRepo.save(driver);


        }catch (IOException e){
            throw new DuplicateEntryException("id Duplicate");
        }


        return null;
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


}
