package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class ContactSyncApplication {
    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ContactSyncApplication.class);

        // Determine if running in standalone mode
        boolean standalone = Arrays.stream(args)
                .anyMatch(a -> a.equalsIgnoreCase("--mode=standalone")) ||
                "standalone".equalsIgnoreCase(System.getenv("MODE"));

        if (standalone) {
            app.setWebApplicationType(WebApplicationType.NONE);
        }

        ConfigurableApplicationContext context = app.run(args);

        if (standalone) {
            System.out.println("HEHEHEHEHEH YES");
            context.close();
        }
//        SpringApplication.run(ContactSyncApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runLogic() {
//        return args -> {
//            // This runs only if web is enabled
//            System.out.println("HEHEHEHEHEH YES");
//        };
//    }
}
