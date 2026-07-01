package org.dbbrowser;

import org.dbbrowser.config.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "org.dbbrowser")
public class DbBrowserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbBrowserApplication.class, args);
    }
}