package ru.smart4it.vacancycollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VacancyApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacancyApplication.class, args);
    }

}
