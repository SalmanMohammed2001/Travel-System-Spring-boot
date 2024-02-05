package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking,String> {

}
