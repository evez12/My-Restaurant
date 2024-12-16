package com.huseynov.restaurant;

import com.huseynov.restaurant.enums.UserRole;
import com.huseynov.restaurant.model.Role;
import com.huseynov.restaurant.repository.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantMsApplication {

    public static void main(String[] args) {
//        System.out.println("Project running");
        SpringApplication.run(RestaurantMsApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepo roleRepo) {
        return (a) -> {
            Role role = new Role();
            role.setName(UserRole.CUSTOMER);
            System.out.println("ROLE: "+roleRepo.save(role));
//            System.out.println("ROLE: "+role);
        };
    }

}
