package com.travelserviceapi.travelserviceapi.config.permission;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


import static com.travelserviceapi.travelserviceapi.config.permission.ApplicationUserPermission.*;

@Getter
public enum ApplicationUserRole {


    USER(Sets.newHashSet(ORDER_WRITE,CUSTOMER_READ,CUSTOMER_WRITE)),
    MANAGER(Sets.newHashSet(ORDER_READ,PRODUCT_READ,CUSTOMER_READ)),
    ADMIN(Sets.newHashSet(ORDER_READ,ORDER_WRITE,PRODUCT_READ,PRODUCT_WRITE,CUSTOMER_WRITE,CUSTOMER_READ));


    private final Set<ApplicationUserPermission> permission;


    ApplicationUserRole(Set<ApplicationUserPermission> permission) {
        this.permission = permission;
    }


    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){

        Set<SimpleGrantedAuthority> permissions = getPermission().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return permissions;

    }

}
