package callhub.connect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CallhubconnectApplication {

	@Value("${azure}")
	private String azureEndpoint;

	public static void main(String[] args) {
		SpringApplication.run(CallhubconnectApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("azureEndpoint");
				registry.addMapping("/**").allowedOrigins("http://localhost:8080/");
				registry.addMapping("/**").allowedOrigins("http://localhost:3000/");
				registry.addMapping("/**").allowedOrigins("https://callhub.netlify.app/");
			}
		};
	}

}
