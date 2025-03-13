package by.yurhilevich.WebApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BackendServer {

	public static void main(String[] args) {
		log.info("Start logger work");
		SpringApplication.run(BackendServer.class, args);
	}

}
