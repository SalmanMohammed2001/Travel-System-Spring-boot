package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.PackageDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageDetailsRepo extends JpaRepository<PackageDetails,String> {
}
