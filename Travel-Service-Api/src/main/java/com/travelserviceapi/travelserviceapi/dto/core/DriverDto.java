package com.travelserviceapi.travelserviceapi.dto.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DriverDto {

    private String driverId;
    private String driverName;
    private String driverContact;
    private String driverNic;
    private byte[] driverImage;
    private byte[] licenseImageFront;
    private byte[] licenseImageRear;
    private boolean driverStatus;
    private String vehicle;
}

