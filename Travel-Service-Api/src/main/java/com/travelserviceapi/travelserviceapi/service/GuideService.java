package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestGuideDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseGuideDto;

import java.io.IOException;

public interface GuideService {

    public ResponseGuideDto saveGuide(RequestGuideDto dto) throws IOException;
    public ResponseGuideDto findId(String guideId) throws IOException;
    public ResponseGuideDto update(RequestGuideDto dto) throws IOException;

}
