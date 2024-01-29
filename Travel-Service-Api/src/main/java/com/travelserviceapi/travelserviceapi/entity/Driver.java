package com.travelserviceapi.travelserviceapi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Driver {
    @Id

    private String driverId;
    private String driverName;
    private String driverContact;
    @Column(unique = true)
    private String driverNic;
    private String driverImage;
    private String licenseImageFront;
    private String licenseImageRear;
    private boolean driverStatus;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "vehicle_id",referencedColumnName = "vehicleId",nullable = false,unique = true)
    private Vehicle vehicle;

}
