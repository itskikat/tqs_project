package deti.tqs.g305.handymanservicesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class HandymanServicesAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandymanServicesAppApplication.class, args);
    }

}
