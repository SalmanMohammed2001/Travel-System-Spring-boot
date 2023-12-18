package com.travelserviceapi.travelserviceapi.dto.responseDto;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestVehicleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDriverDto {
    private int driverId;
    private String driverName;
    private String driverContact;
    private String driverNic;
    private byte[] driverImage;
    private byte[] licenseImageFront;
    private byte[] licenseImageRear;
    private boolean driverStatus;
    private ResponseVehicleDto vehicle;
}
