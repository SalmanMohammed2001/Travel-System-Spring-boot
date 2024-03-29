package com.travelserviceapi.travelserviceapi.embadded;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Embeddable
public class Price {
    private String PriceKey;
    private String PriceValue;
}
