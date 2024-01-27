package com.travelserviceapi.travelserviceapi.entity;

import com.travelserviceapi.travelserviceapi.embadded.TravelDuration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class PackageDetails {

    @Id
    private String packageId;
    private String category;
    @Embedded
    private TravelDuration travelDuration;
    private String packageTravelArea;
    private String packageNoAdult;
    private String packageNoChildren;
    private String PackageTotalHeadCount;
    private String withPetOrNo;
    private double packageValue;
    private boolean packageStatus;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "hotel_id",referencedColumnName = "hotelId",nullable = false,unique = true)
    private Hotel hotel;

/*
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "hotel_id",referencedColumnName = "hotelId",nullable = false)
    private Hotel hotel;
*/

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "vehicle_id",referencedColumnName = "vehicleId",nullable = false,unique = true)
    private Vehicle vehicle;

/*
  @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
  @JoinColumn(name = "vehicle_id",referencedColumnName = "vehicleId",nullable = false)
  private Vehicle vehicle;
*/


;


    @OneToMany(mappedBy = "packageDetails", cascade = CascadeType.ALL)
    private List<BookingDetails> bookingDetailsLis;






}
