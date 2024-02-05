package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverRepo  extends JpaRepository<Driver,String> {

    public Driver findByDriverNic(String nic);

    public boolean existsByDriverNic(String nic);


    @Query(value = "SELECT COUNT(*) AS hotel_count FROM driver",nativeQuery = true)
    public long  findAllDriverCount();
}
