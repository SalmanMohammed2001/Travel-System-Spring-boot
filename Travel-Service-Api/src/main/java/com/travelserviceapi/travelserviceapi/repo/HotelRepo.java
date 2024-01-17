package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Hotel;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HotelRepo extends JpaRepository<Hotel,String> {
    public List<Hotel> findAllByHotelCategoryEquals(String category);
}
