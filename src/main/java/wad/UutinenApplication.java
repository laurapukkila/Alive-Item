package wad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages={"wad.configuration", "wad", "wad.controller", "wad.domain", "wad.repository"})
public class UutinenApplication {

    public static void main(String[] args) {
        SpringApplication.run(UutinenApplication.class, args);
    }
}
