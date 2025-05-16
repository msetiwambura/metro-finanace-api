package co.tz.metro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetroApplication {

    public static void main(String[] args) {
        System.out.println("Starting Metro Application ----------------->");
        SpringApplication.run(MetroApplication.class, args);
    }

}
