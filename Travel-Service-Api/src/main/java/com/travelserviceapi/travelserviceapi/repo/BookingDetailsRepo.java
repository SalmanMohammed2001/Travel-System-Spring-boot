package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.BookingDetails;
import com.travelserviceapi.travelserviceapi.entity.BookingDetails_Pk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingDetailsRepo extends JpaRepository<BookingDetails, BookingDetails_Pk> {


}
