package goorm.boardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardapiApplication.class, args);
	}

}
