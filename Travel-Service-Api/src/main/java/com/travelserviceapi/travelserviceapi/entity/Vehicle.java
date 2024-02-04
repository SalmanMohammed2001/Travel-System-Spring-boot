package com.travelserviceapi.travelserviceapi.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestPart;

import java.awt.*;
import java.util.List;

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
    private String vehicleFuelUsage;
    private String vehicleHybrid;
    private String vehicleTransmission;
    @Column(columnDefinition = "Text")
    private String vehicleImages;
    private int vehicleQty;
    private  boolean vehicleState;


    @OneToOne( mappedBy = "vehicle")
    private Driver driver;

    @OneToOne(mappedBy = "vehicle")
    private PackageDetails  packageDetails;

/*    @OneToMany(mappedBy = "vehicle")
    private List<PackageDetails> packageDetails;*/


}
