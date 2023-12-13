package com.travelserviceapi.travelserviceapi.embadded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Embeddable
public class TravelDuration {
    private String startDate;
    private String endDate;
    private int days;
    private int night;
}
