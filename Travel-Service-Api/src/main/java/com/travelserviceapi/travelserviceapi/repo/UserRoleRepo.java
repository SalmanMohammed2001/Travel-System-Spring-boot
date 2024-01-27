package com.travelserviceapi.travelserviceapi.repo;


import com.travelserviceapi.travelserviceapi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRoleRepo extends JpaRepository<UserRole,String> {
}
