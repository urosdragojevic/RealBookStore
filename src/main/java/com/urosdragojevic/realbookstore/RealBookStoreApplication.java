package com.urosdragojevic.realbookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class RealBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealBookStoreApplication.class, args);
    }

}
