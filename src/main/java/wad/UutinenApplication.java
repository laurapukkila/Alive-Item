package wad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication (scanBasePackages={"wad.configuration", "wad", "wad.controller", "wad.domain", "wad.repository"})
@EnableJpaRepositories("wad.repository.UutinenRepository")
public class UutinenApplication {

    public static void main(String[] args) {
        SpringApplication.run(UutinenApplication.class, args);
    }
}
