package callhub.connect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
public class CallhubconnectApplication {

	@Value("${azure}")
	private String azureEndpoint;

	public static void main(String[] args) {
		SpringApplication.run(CallhubconnectApplication.class, args);
	}

	/**
	 * Configures Cross-Origin Resource Sharing (CORS) for the Callhub application.
	 *
	 * @return A WebMvcConfigurer object that defines CORS configuration for the application.
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").
				allowedOrigins(
					"http://localhost:8080",
					"http://localhost:3000",
					"https://callhub.netlify.app",
					azureEndpoint // Use the actual value of azureEndpoint
				).allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.allowCredentials(true);

			}
		};
	}

}
