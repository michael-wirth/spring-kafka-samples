package demo.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DlqProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DlqProducerApplication.class, args);
	}

}
