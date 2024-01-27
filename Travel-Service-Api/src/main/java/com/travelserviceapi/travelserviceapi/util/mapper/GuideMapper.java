package com.travelserviceapi.travelserviceapi.util.mapper;

import com.travelserviceapi.travelserviceapi.dto.core.GuideDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestGuideDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuideMapper {

 GuideDto toGuideDto(RequestGuideDto dto);


}
