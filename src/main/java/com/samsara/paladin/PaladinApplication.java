package com.samsara.paladin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaladinApplication {

    protected PaladinApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(PaladinApplication.class, args);
    }

}
