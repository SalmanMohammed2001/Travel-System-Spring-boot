package com.travelserviceapi.travelserviceapi.embadded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contact {
    private String contact1;
    private String contact2;
}
