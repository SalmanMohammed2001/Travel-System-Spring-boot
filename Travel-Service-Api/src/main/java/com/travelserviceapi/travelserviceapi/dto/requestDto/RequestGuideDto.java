package com.travelserviceapi.travelserviceapi.dto.requestDto;

import com.travelserviceapi.travelserviceapi.entity.Booking;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class RequestGuideDto {
    private String guideId;
    private String guideName;
    private String guideAddress;
    private String guideContact;
    private String guideBirthDate;
    private double guideManDayValue;
    private String guideExperience;
    private byte[] guideIdFrontImage;
    private byte[] guideIdRearImage;
    private byte[] guideNicFrontImag;
    private byte[] guideNicRearImage;
    private byte[] guideProfilePicImage;
    private boolean guideStatus;

}
