package com.travelserviceapi.travelserviceapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandResponse {
    private  int coed;
    private String message;
    private Object data;
}
