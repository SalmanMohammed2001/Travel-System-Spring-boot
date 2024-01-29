package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.config.permission.ApplicationUserRole;
import com.travelserviceapi.travelserviceapi.dto.securitycore.ApplicationUser;
import com.travelserviceapi.travelserviceapi.entity.User;
import com.travelserviceapi.travelserviceapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.travelserviceapi.travelserviceapi.config.permission.ApplicationUserRole.*;

@Service
public class ApplicationUserServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public ApplicationUserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByUsername= userRepo.findByUserEmail(username);
        if(!userRepo.existsByUserEmail(username)){
            throw new UsernameNotFoundException(String.format("Username %s not found",username));
        }

        Set<SimpleGrantedAuthority> grantedAuthorities =new HashSet<>();
        switch (userByUsername.getUserRole().getRoleName()){
            case "USER":
                grantedAuthorities= USER.getGrantedAuthorities();
                break;

            case "ADMIN":
                grantedAuthorities=ADMIN.getGrantedAuthorities();
                break;

            case "MANAGER":
                grantedAuthorities=MANAGER.getGrantedAuthorities();
                break;

            default:
                return  null;
        }

        ApplicationUser applicationUser=new ApplicationUser(
                grantedAuthorities,
                userByUsername.getUserEmail(),
                userByUsername.getUserPassword(),
                userByUsername.isAccountNonExpired(),
                userByUsername.isAccountNonLock(),
                userByUsername.isCredentialsNoExpired(),
                userByUsername.isEnable()
        );

        return applicationUser;
    }
}
