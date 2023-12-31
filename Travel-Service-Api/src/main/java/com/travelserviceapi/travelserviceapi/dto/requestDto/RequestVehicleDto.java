package com.travelserviceapi.travelserviceapi.dto.requestDto;

import com.travelserviceapi.travelserviceapi.entity.Driver;
import com.travelserviceapi.travelserviceapi.entity.PackageDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestVehicleDto {

    private String vehicleId;
    private String vehicleName;
    private double vehiclePriceFor1Km;
    private String vehicleCategory;
    private String vehicleType;
    private double vehiclePriceFor100Km;
    private String vehicleFuelType;
    private int vehicleSeatCapacity;
    private double vehicleFuelUsage;
    private String vehicleHybrid;
    private String vehicleTransmission;
    private ArrayList<byte[]> vehicleImages;
    private int vehicleQty;
    private  boolean vehicleState;

}
