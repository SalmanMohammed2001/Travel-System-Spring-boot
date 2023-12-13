package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<User,String> {

    public User findByUserEmail(String email);

}
