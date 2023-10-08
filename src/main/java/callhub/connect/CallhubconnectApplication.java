package callhub.connect;

import callhub.connect.use_case.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class CallhubconnectApplication {



	public static void main(String[] args) {
		SpringApplication.run(CallhubconnectApplication.class, args);
	}

}
