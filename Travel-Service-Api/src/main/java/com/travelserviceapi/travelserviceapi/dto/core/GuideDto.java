package com.travelserviceapi.travelserviceapi.dto.core;

import com.travelserviceapi.travelserviceapi.entity.Booking;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class GuideDto {
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
    private String guideProfilePicImage;
    private boolean guideStatus;
    private Booking booking;
}
