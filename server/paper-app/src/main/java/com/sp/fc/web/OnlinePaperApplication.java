package com.sp.fc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.sp.fc.config",
        "com.sp.fc.web",
        "com.sp.fc.site"
})
@EnableJpaRepositories(basePackages = {
        "com.sp.fc.config"
})

public class OnlinePaperApplication {
    public static void main(String[] args) {
            SpringApplication.run(OnlinePaperApplication.class,args);
        }
}
