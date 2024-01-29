package com.travelserviceapi.travelserviceapi.embadded;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

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
