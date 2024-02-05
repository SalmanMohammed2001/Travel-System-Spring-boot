package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking,String> {

    @Query(value = "SELECT  booking_date AS year, SUM(booking_price) AS yearly_income FROM booking GROUP BY booking_date ORDER BY booking_date",nativeQuery = true)
    public Optional<Booking> findByYearlyIncome();

}
