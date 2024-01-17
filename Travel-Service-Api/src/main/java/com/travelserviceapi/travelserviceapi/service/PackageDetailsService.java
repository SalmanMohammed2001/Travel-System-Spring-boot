package com.travelserviceapi.travelserviceapi.service;


import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestDriverDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestPackageDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseDriverDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponsePackageDetailsDto;

import java.io.IOException;
import java.util.List;

public interface PackageDetailsService {
    public ResponsePackageDetailsDto save(RequestPackageDetailsDto dto);
    public ResponsePackageDetailsDto findById(String id) throws IOException;
    public ResponsePackageDetailsDto update(RequestPackageDetailsDto dto);
    public void delete(String id);
    public List<ResponsePackageDetailsDto> findAll() throws IOException;


}
