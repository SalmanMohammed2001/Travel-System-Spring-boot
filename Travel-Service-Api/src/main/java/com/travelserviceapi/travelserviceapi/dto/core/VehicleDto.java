package com.travelserviceapi.travelserviceapi.dto.core;

import com.travelserviceapi.travelserviceapi.entity.Driver;
import com.travelserviceapi.travelserviceapi.entity.PackageDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private String vehicleId;
    private String vehicleName;
    private double vehiclePriceFor1Km;
    private String vehicleCategory;
    private String vehicleType;
    private double vehiclePriceFor100Km;
    private String vehicleFuelType;
    private int vehicleSeatCapacity;
    private String vehicleFuelUsage;
    private String vehicleHybrid;
    private String vehicleTransmission;
    private ArrayList<byte[]> vehicleImages;
    private int vehicleQty;
    private Driver driver;
    private PackageDetails packageDetails;
    private  boolean vehicleState;
    private byte[] vehicleFrontImage;
}
