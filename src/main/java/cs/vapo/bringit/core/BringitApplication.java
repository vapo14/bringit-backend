package cs.vapo.bringit.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "cs.vapo.bringit.core")
@EnableJpaRepositories
public class BringitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BringitApplication.class, args);
	}

}
