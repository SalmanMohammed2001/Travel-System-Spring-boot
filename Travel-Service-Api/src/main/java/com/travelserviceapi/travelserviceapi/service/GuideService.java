package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestGuideDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseGuideDto;

import java.io.IOException;
import java.util.List;

public interface GuideService {

    public ResponseGuideDto saveGuide(RequestGuideDto dto) throws IOException;
    public ResponseGuideDto findId(String guideId) throws IOException;
    public ResponseGuideDto update(RequestGuideDto dto) throws IOException;

    public void  delete(String id);

    public List<ResponseGuideDto> findAll();

}
