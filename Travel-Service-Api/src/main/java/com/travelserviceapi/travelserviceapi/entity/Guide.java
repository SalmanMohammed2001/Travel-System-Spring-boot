package com.travelserviceapi.travelserviceapi.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Guide {
    @Id
    private String guideId;
    private String guideName;
    private String guideAddress;
    private String guideContact;
    private String guideBirthDate;
    private double guideManDayValue;
    private String guideExperience;
    private String guideIdFrontImage;
    private String guideIdRearImage;
    private String guideNicFrontImag;
    private String guideNicRearImage;
    private String guideProfilePicImage;

    private boolean guideStatus;

    @OneToOne(mappedBy = "guide")
    private Booking booking;
}
