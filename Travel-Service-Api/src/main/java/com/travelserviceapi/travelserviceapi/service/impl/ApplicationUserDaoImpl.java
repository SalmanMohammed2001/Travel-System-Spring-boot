package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.common.collect.Lists;

import com.travelserviceapi.travelserviceapi.dto.securitycore.ApplicationUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ApplicationUserDaoImpl implements ApplicationUserDao {

    private  final PasswordEncoder passwordEncoder;

    public ApplicationUserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> findApplicationUserByUserName(String username) {
        return applicationUsers().stream().filter(e->e.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUser> applicationUsers(){
        List<ApplicationUser> applicationUsers= Lists.newArrayList(
                new ApplicationUser(
                        USER.getGrantedAuthorities(),
                        "user1",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        MANAGER.getGrantedAuthorities(),
                        "user2",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        "user3",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUsers;
    }


}
