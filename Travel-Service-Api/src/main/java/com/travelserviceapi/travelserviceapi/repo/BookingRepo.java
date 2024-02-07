package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Booking;

import com.travelserviceapi.travelserviceapi.entity.YearlyIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking,String> {

    @Query(value = "SELECT COUNT(*) AS booking_count FROM booking",nativeQuery = true)
    public long  findAllBookingCount();


    @Query(value = "SELECT * FROM  booking WHERE user_id=?",nativeQuery = true)
    public List<Booking> findBookingByUserId(String userId);

}
