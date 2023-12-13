package com.travelserviceapi.travelserviceapi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class BookingDetails_Pk implements Serializable {
    private String bookingId;
    private String packageId;

}
