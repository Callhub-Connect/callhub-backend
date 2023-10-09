package callhub.connect;

import callhub.connect.use_case.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CallhubconnectApplication {



	public static void main(String[] args) {
		SpringApplication.run(CallhubconnectApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://connect.greenplant-1b2a73a7.eastus.azurecontainerapps.io");
				registry.addMapping("/**").allowedOrigins("http://localhost:8080/");
			}
		};
	}

}
