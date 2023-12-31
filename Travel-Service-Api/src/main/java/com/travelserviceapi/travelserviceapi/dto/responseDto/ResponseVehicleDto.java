package com.travelserviceapi.travelserviceapi.dto.responseDto;

import com.travelserviceapi.travelserviceapi.entity.Driver;
import com.travelserviceapi.travelserviceapi.entity.PackageDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseVehicleDto {

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
