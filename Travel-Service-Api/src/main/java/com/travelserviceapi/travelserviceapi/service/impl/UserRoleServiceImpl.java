package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.entity.UserRole;
import com.travelserviceapi.travelserviceapi.repo.UserRoleRepo;
import com.travelserviceapi.travelserviceapi.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepo userRoleRepo;

    public UserRoleServiceImpl(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public void initializeRole() {

        long count = userRoleRepo.count();
        if (count==0){
            UserRole userRole1 = new UserRole("UR-1","ADMIN","Super Privilege",null);
            UserRole userRole2 = new UserRole("UR-2","MANAGER","Management Privilege",null);
            UserRole userRole3 = new UserRole("UR-3","USER","Regular User",null);
        }

    }
}
