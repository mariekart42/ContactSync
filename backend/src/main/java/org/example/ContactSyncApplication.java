package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Arrays;

@SpringBootApplication
public class ContactSyncApplication {
    public static void main(String[] args) {

        System.out.println("\033[0;33mENTRY ContactSyncApplication\033[0m");

        SpringApplication app = new SpringApplication(ContactSyncApplication.class);

        // Determine if running in standalone mode
        boolean standalone = Arrays.stream(args)
                .anyMatch(a -> a.equalsIgnoreCase("--mode=standalone")) ||
                "standalone".equalsIgnoreCase(System.getenv("MODE"));

        if (standalone) {
            app.setWebApplicationType(WebApplicationType.NONE);
        }

        System.out.println("\033[0;33mApp logic WITH frontend\033[0m");
        ConfigurableApplicationContext context = app.run(args);

        // only backend logic is executed one time, program terminates afterward
        if (standalone) {
            System.out.println("\033[0;33mApp logic without frontend\033[0m");
            App.syncContacts();
            context.close();
        }
    }
}
