package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepo  extends JpaRepository<Driver,String> {

    public Driver findByDriverNic(String nic);

    public boolean existsByDriverNic(String nic);
}
