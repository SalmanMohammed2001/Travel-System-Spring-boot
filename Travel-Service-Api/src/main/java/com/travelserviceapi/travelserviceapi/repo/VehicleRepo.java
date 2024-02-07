package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepo extends JpaRepository<Vehicle,String> {
  public List<Vehicle>  findAllByVehicleStateFalse();
  public List<Vehicle>  findAllByVehicleStateTrueAndVehicleCategoryEquals(String category);

  @Query(value = "SELECT COUNT(*) AS vehicle_count FROM vehicle",nativeQuery = true)
  public long  findAllVehicleCount();



}
