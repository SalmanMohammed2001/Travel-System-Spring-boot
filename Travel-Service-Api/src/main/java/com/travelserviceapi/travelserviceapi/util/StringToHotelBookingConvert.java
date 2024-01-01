package com.travelserviceapi.travelserviceapi.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.travelserviceapi.travelserviceapi.dto.core.PricesDTO;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestBookingDetailsDto;
import org.springframework.core.convert.converter.Converter;


import java.util.ArrayList;

public class StringToHotelBookingConvert  implements Converter<String, ArrayList<RequestBookingDetailsDto>> {
    @Override
    public ArrayList<RequestBookingDetailsDto> convert(String source) {
        return new Gson().fromJson(source, new TypeToken<ArrayList<RequestBookingDetailsDto>>(){}.getType());
    }
}
