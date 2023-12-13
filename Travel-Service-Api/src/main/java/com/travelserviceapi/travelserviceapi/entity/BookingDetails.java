package com.travelserviceapi.travelserviceapi.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@IdClass(BookingDetails_Pk.class)
public class BookingDetails {

    @Id
    private String bookingId;

    @Id
    private String packageId;

    private String date;


    private double total;

    private String bankSlip;


    @ManyToOne()
    @JoinColumn(name = "bookingId",referencedColumnName = "bookingId")
    private Booking booking;

    @ManyToOne()
    @JoinColumn(name = "packageId",referencedColumnName = "packageId")
    private PackageDetails packageDetails;



}
