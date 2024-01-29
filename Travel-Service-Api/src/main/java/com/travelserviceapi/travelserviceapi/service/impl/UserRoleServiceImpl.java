package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.entity.User;
import com.travelserviceapi.travelserviceapi.entity.UserRole;
import com.travelserviceapi.travelserviceapi.repo.UserRepo;
import com.travelserviceapi.travelserviceapi.repo.UserRoleRepo;
import com.travelserviceapi.travelserviceapi.service.UserRoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepo userRoleRepo;
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;



    public UserRoleServiceImpl(UserRoleRepo userRoleRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRoleRepo = userRoleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initializeRole() {
        List<UserRole> all = userRoleRepo.findAll();


        if (all.isEmpty()){
            UserRole Admin=new UserRole("UR-1","ADMIN","Super Privileges",null);
            UserRole Manager=new UserRole("UR-2","MANAGER","Manager Privileges",null);
            UserRole User=new UserRole("UR-3","USER","Regular User ",null);

            userRoleRepo.saveAll(List.of(Admin,Manager,User));
        }
        UserRole role1=new UserRole("UR-1","ADMIN","Super Privileges",null);

        if(userRepo.findAllAdmins("UR-1").isEmpty()){
            User user = new User(
                    "1",
                    "Anna",
                    "Ana@gmail.com",
                    passwordEncoder.encode("1234"),
                    "110",
                    "2020-10-30",
                    "female",
                    new Contact("119","9654"),
                    "colombo",
                    null,
                    null,
                    null,
                    true,
                    true,
                    true,
                    true,
                    true,
                    "011",
                    null,
                    null,
                    role1);
          userRepo.save(user);
        }



    }
}
