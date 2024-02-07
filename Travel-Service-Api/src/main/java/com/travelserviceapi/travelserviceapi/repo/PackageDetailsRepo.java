package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.PackageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackageDetailsRepo extends JpaRepository<PackageDetails,String> {

    @Query(value = "SELECT COUNT(*) AS package_count FROM package_details",nativeQuery = true)
    public long  findAllPackageCount();
}
