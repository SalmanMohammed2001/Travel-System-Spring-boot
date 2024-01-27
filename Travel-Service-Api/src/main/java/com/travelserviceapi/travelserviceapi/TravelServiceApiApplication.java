package com.travelserviceapi.travelserviceapi;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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


    @Override
    public void run(String... args) throws Exception {
    //save user role
    //save default user (must be admin)
    }
}
