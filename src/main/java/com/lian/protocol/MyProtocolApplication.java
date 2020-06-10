package com.lian.protocol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyProtocolApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyProtocolApplication.class, args);
    }

}
