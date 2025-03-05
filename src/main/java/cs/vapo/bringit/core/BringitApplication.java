package cs.vapo.bringit.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "cs.vapo.bringit.core")
public class BringitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BringitApplication.class, args);
	}

}
