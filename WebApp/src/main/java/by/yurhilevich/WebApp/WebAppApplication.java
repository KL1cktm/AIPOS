package by.yurhilevich.WebApp;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		log.info("Start logger work");
		SpringApplication.run(WebAppApplication.class, args);
	}

}
