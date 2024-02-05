package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Hotel;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HotelRepo extends JpaRepository<Hotel,String> {
    public List<Hotel> findAllByHotelCategoryEquals(String category);


    @Query(value = "SELECT COUNT(*) AS hotel_count FROM hotel",nativeQuery = true)
    public long  findAllHotelCount();
}
