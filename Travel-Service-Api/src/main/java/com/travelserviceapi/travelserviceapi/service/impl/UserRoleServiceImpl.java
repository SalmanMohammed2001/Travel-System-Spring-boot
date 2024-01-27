package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.entity.UserRole;
import com.travelserviceapi.travelserviceapi.repo.UserRepo;
import com.travelserviceapi.travelserviceapi.repo.UserRoleRepo;
import com.travelserviceapi.travelserviceapi.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepo userRoleRepo;
    private final UserRepo userRepo;



    public UserRoleServiceImpl(UserRoleRepo userRoleRepo, UserRepo userRepo) {
        this.userRoleRepo = userRoleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void initializeRole() {
        List<UserRole> all = userRoleRepo.findAll();
        if (all.isEmpty()){
            UserRole userRole1 = new UserRole("UR-1","ADMIN","Super Privilege",null);
            UserRole userRole2 = new UserRole("UR-2","MANAGER","Management Privilege",null);
            UserRole userRole3 = new UserRole("UR-3","USER","Regular User",null);

            userRoleRepo.saveAll(List.of(userRole1,userRole2,userRole3));
        }




    }
}
