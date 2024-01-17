package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelRepo extends JpaRepository<Hotel,String> {
}
