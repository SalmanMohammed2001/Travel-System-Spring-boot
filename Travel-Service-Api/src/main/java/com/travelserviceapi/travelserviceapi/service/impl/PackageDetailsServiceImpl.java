package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.core.PackageDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestPackageDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.*;
import com.travelserviceapi.travelserviceapi.entity.*;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.exception.EntryNotFoundException;
import com.travelserviceapi.travelserviceapi.repo.PackageDetailsRepo;
import com.travelserviceapi.travelserviceapi.service.PackageDetailsService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class PackageDetailsServiceImpl implements PackageDetailsService {

    private final PackageDetailsRepo packageDetailsRepo;

    private final ModelMapper mapper;



    private final Gson gson;

    private final Generator generator;
    public PackageDetailsServiceImpl(PackageDetailsRepo packageDetailsRepo, ModelMapper mapper, Gson gson, Generator generator) {
        this.packageDetailsRepo = packageDetailsRepo;
        this.mapper = mapper;

        this.gson = gson;
        this.generator = generator;
    }

    @Override
    public ResponsePackageDetailsDto save(RequestPackageDetailsDto dto) {
        try {
            String generateKey = generator.generateKey("Package");
            PackageDetailsDto packageDetailsDto = mapper.map(dto, PackageDetailsDto.class);
            packageDetailsDto.setPackageId(generateKey);

            if(!packageDetailsRepo.existsById(packageDetailsDto.getPackageId())){
                Hotel hotel = new Hotel();
                hotel.setHotelId(packageDetailsDto.getHotel());



                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(packageDetailsDto.getVehicle());


                PackageDetails packageDetails = mapper.map(packageDetailsDto, PackageDetails.class);
                packageDetails.setHotel(hotel);
                packageDetails.setVehicle(vehicle);

                PackageDetails save = packageDetailsRepo.save(packageDetails);

               ResponseVehicleDto responseVehicleDto= mapper.map(save.getVehicle(),ResponseVehicleDto.class);
                ResponseHotelDto responseHotelDto = mapper.map(save.getHotel(), ResponseHotelDto.class);
               // responseHotelDto.setPackageDetails(null);

                ResponsePackageDetailsDto responsePackageDetailsDto = mapper.map(save, ResponsePackageDetailsDto.class);
                responsePackageDetailsDto.setHotel(responseHotelDto);
                responsePackageDetailsDto.setVehicle(responseVehicleDto);
                return responsePackageDetailsDto;

            }else {
                throw new DuplicateEntryException("id Duplicate");
            }

        }catch (Exception e){
            throw new DuplicateEntryException("id Duplicate");
        }
    }

    @Override
    public ResponsePackageDetailsDto findById(String id) throws IOException {
        if(packageDetailsRepo.existsById(id)){
            System.out.println(id);
           PackageDetails packageDetails = packageDetailsRepo.findById(id).get();
            ResponseVehicleDto responseVehicleDto= mapper.map(packageDetails.getVehicle(),ResponseVehicleDto.class);
            ResponseHotelDto responseHotelDto = mapper.map(packageDetails.getHotel(), ResponseHotelDto.class);
           // responseHotelDto.setPackageDetails(null);
            importImages(responseVehicleDto,packageDetails.getVehicle(),responseHotelDto,packageDetails.getHotel());

            ResponsePackageDetailsDto responsePackageDetailsDto = mapper.map(packageDetails, ResponsePackageDetailsDto.class);
         responsePackageDetailsDto.setHotel(responseHotelDto);
      responsePackageDetailsDto.setVehicle(responseVehicleDto);
            System.out.println(responseHotelDto);
            return responsePackageDetailsDto  ;
        }else {
            throw new EntryNotFoundException("Id Not found");
        }
    }

    @Override
    public ResponsePackageDetailsDto update(RequestPackageDetailsDto dto) {
        if(packageDetailsRepo.existsById(dto.getPackageId())){
            PackageDetailsDto packageDetailsDto = mapper.map(dto, PackageDetailsDto.class);
            Hotel hotel = new Hotel();
            hotel.setHotelId(packageDetailsDto.getHotel());
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(packageDetailsDto.getVehicle());


            PackageDetails packageDetails = mapper.map(packageDetailsDto, PackageDetails.class);
            packageDetails.setHotel(hotel);
            packageDetails.setVehicle(vehicle);

            PackageDetails save = packageDetailsRepo.save(packageDetails);


            ResponseVehicleDto responseVehicleDto= mapper.map(save.getVehicle(),ResponseVehicleDto.class);
            ResponseHotelDto responseHotelDto = mapper.map(save.getHotel(), ResponseHotelDto.class);
          //  responseHotelDto.setPackageDetails(null);

            ResponsePackageDetailsDto responsePackageDetailsDto = mapper.map(save, ResponsePackageDetailsDto.class);
            responsePackageDetailsDto.setHotel(responseHotelDto);
            responsePackageDetailsDto.setVehicle(responseVehicleDto);
            return responsePackageDetailsDto;

        }else {
            throw new EntryNotFoundException("Id Not found");
        }

    }

    @Override
    public void delete(String id) {
        if (packageDetailsRepo.existsById(id)){
            packageDetailsRepo.deleteById(id);
        }else {
            throw new EntryNotFoundException("Id Not found");
        }
    }

    @Override
    public List<ResponsePackageDetailsDto> findAll() throws IOException {
        List<PackageDetails> all = packageDetailsRepo.findAll();
        List<ResponsePackageDetailsDto> responsePackageDetailsDto=mapper.map(all,new TypeToken<List<ResponsePackageDetailsDto>>(){}.getType());
        List<ResponsePackageDetailsDto> responsePackageDetailsDtos = importImagesAll(responsePackageDetailsDto, all);
        return responsePackageDetailsDtos;
    }

    public void importImages(ResponseVehicleDto vehicleDto,Vehicle vehicle,ResponseHotelDto responseHotelDto,Hotel hotel) throws IOException {

        String images = vehicle.getVehicleImages();
        vehicleDto.setVehicleImages(new ArrayList<>());
        ArrayList<String> imageList = gson.fromJson(images, new com.google.gson.reflect.TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            vehicleDto.getVehicleImages().add(imgData);
        }


        String images2 = hotel.getImages();
        responseHotelDto.setImages(new ArrayList<>());
        ArrayList<String> imageList1 = gson.fromJson(images2, new com.google.gson.reflect.TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList1.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            responseHotelDto.getImages().add(imgData);
        };
    }




    public List<ResponsePackageDetailsDto> importImagesAll(List<ResponsePackageDetailsDto> packageDetailsDto ,List<PackageDetails>all) throws IOException {
        if(packageDetailsDto.isEmpty() && all.isEmpty()){
            throw new EntryNotFoundException("vehicle not found");
        }



      /*  ResponseDriverDto responseDriverDto = new ResponseDriverDto();
        all.forEach(data->{

            try {
                BufferedImage read = ImageIO.read(new File(data.getDriverImage()));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(read, "jpg", baos);
                byte[] bytes = baos.toByteArray();
                responseDriverDto.setDriverImage(bytes);


                read = ImageIO.read(new File(data.getLicenseImageFront()));
                baos = new ByteArrayOutputStream();
                ImageIO.write(read, "jpg", baos);
                bytes = baos.toByteArray();
                responseDriverDto.setLicenseImageFront(bytes);

                read = ImageIO.read(new File(data.getLicenseImageRear()));
                baos = new ByteArrayOutputStream();
                ImageIO.write(read, "jpg", baos);
                bytes = baos.toByteArray();
                responseDriverDto.setLicenseImageRear(bytes);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });*/


        Hotel hotel = new Hotel();
        Vehicle vehicle = new Vehicle();
        all.forEach(data->{
            hotel.setImages(data.getHotel().getImages());
            vehicle.setVehicleImages(data.getVehicle().getVehicleImages());
        });

        String images = vehicle.getVehicleImages();
        ResponseVehicleDto responseVehicleDto = new ResponseVehicleDto();
        responseVehicleDto.setVehicleImages(new ArrayList<>());
        ArrayList<String> imageList = gson.fromJson(images, new com.google.gson.reflect.TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            responseVehicleDto.getVehicleImages().add(imgData);
        };


        String images2 = hotel.getImages();
        ResponseHotelDto responseHotelDto = new ResponseHotelDto();
        responseHotelDto.setImages(new ArrayList<>());
        ArrayList<String> imageList1 = gson.fromJson(images2, new com.google.gson.reflect.TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList1.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            responseHotelDto.getImages().add(imgData);
        };

        List<ResponsePackageDetailsDto> arrayList = new ArrayList<>();
        for (ResponsePackageDetailsDto data:packageDetailsDto){
            ResponseVehicleDto vehicle1 = data.getVehicle();
            vehicle1.setVehicleImages(responseVehicleDto.getVehicleImages());
            ResponseHotelDto hotel1=data.getHotel();
            hotel1.setImages(responseHotelDto.getImages());

            arrayList.add(new ResponsePackageDetailsDto(
                    data.getPackageId(),
                    data.getCategory(),
                    data.getTravelDuration(),
                    data.getPackageTravelArea(),
                    data.getPackageNoAdult(),
                    data.getPackageNoChildren(),
                    data.getPackageTotalHeadCount(),
                    data.getWithPetOrNo(),
                    data.getPackageValue(),
                    data.isPackageStatus(),
                    hotel1,
                    vehicle1

            ));
        }
        return arrayList;
    }

}
