package com.travelserviceapi.travelserviceapi.entity;


import lombok.*;

import javax.persistence.*;

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
    private String packageCategory;
    private String packageStartDate;
    private String packageEndDate;
    private double packageValue;
    private String userName;

    @ManyToOne()
    @JoinColumn(name = "bookingId",referencedColumnName = "bookingId",insertable = false,updatable = false)
    private Booking booking;

    @ManyToOne()
    @JoinColumn(name = "packageId",referencedColumnName = "packageId",insertable = false,updatable = false)
    private PackageDetails packageDetails;



}
