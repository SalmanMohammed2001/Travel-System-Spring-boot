package com.travelserviceapi.travelserviceapi;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.entity.UserRole;
import com.travelserviceapi.travelserviceapi.repo.UserRoleRepo;
import com.travelserviceapi.travelserviceapi.service.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TravelServiceApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TravelServiceApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    @Bean
    public Gson getGson(){
        return new Gson();
    }


    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void run(String... args) throws Exception {
    //save user role
    //save default user (must be admin)

    userRoleService.initializeRole();

    }
}
