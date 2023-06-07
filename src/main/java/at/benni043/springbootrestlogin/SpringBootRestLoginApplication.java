package at.benni043.springbootrestlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringBootRestLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestLoginApplication.class, args);
	}

}
