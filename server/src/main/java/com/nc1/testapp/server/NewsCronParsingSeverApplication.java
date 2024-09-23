package com.nc1.testapp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.nc1.testapp.common", "com.nc1.testapp.server"})
public class NewsCronParsingSeverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsCronParsingSeverApplication.class, args);
    }
}
