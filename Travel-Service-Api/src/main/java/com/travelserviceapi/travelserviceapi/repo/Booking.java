package com.travelserviceapi.travelserviceapi.repo;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;

public interface Booking extends JpaAttributeConverter<Booking,String> {
}
