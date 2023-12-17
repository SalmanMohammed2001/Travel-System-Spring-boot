package com.travelserviceapi.travelserviceapi.dto.requestDto;

import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDriverDto {

    private String driverName;
    private String driverContact;
    private String driverNic;
    private byte[] driverImage;
    private byte[] licenseImageFront;
    private byte[] licenseImageRear;
    private boolean driverStatus;
    private RequestVehicleDto vehicle;
}
