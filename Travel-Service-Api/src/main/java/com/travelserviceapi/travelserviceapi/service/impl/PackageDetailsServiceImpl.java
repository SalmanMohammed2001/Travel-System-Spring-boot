package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.dto.core.DriverDto;
import com.travelserviceapi.travelserviceapi.dto.core.PackageDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestPackageDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseHotelDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponsePackageDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class PackageDetailsServiceImpl implements PackageDetailsService {

    private final PackageDetailsRepo packageDetailsRepo;

    private final ModelMapper mapper;

    private final Generator generator;
    public PackageDetailsServiceImpl(PackageDetailsRepo packageDetailsRepo, ModelMapper mapper, Generator generator) {
        this.packageDetailsRepo = packageDetailsRepo;
        this.mapper = mapper;
        this.generator = generator;
    }

    @Override
    public ResponsePackageDetailsDto save(RequestPackageDetailsDto dto) {
        try {
            String generateKey = generator.generateKey("Next");
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
    public ResponsePackageDetailsDto findById(String id) {
        if(packageDetailsRepo.existsById(id)){

            System.out.println(id);
           PackageDetails packageDetails = packageDetailsRepo.findById(id).get();
            ResponseVehicleDto responseVehicleDto= mapper.map(packageDetails.getVehicle(),ResponseVehicleDto.class);
            ResponseHotelDto responseHotelDto = mapper.map(packageDetails.getHotel(), ResponseHotelDto.class);
           // responseHotelDto.setPackageDetails(null);


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
    public List<ResponsePackageDetailsDto> findAll() {
        List<PackageDetails> all = packageDetailsRepo.findAll();
        List<ResponsePackageDetailsDto> responsePackageDetailsDto=mapper.map(all,new TypeToken<List<ResponsePackageDetailsDto>>(){}.getType());
       return responsePackageDetailsDto;
    }
}
