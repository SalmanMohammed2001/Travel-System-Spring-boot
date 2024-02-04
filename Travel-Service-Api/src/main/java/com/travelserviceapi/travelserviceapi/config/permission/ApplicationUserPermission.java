package com.travelserviceapi.travelserviceapi.config.permission;

public enum ApplicationUserPermission {
    BOOKING_READ("booking:read"),
    BOOKING_WRITE("booking:write"),
    PACKAGE_READ("package:read"),
    PACKAGE_WRITE("package:write"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),
    HOTEL_READ("hotel:read"),
    HOTEL_WRITE("hotel:write"),
    VEHICLE_READ("vehicle:read"),
    VEHICLE_WRITE("vehicle:write"),
    DRIVER_READ("driver:read"),
    DRIVER_WRITE("driver:write");

    private final String permission;


    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return  permission;
    }
}
