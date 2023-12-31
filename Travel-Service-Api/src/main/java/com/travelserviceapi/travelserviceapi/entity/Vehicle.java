package com.travelserviceapi.travelserviceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestPart;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
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
    private String vehicleImages;
    private int vehicleQty;
    private  boolean vehicleState;

    @OneToOne( mappedBy = "vehicle")
    private Driver driver;

    @OneToOne(mappedBy = "vehicle")
    private PackageDetails  packageDetails;


}
