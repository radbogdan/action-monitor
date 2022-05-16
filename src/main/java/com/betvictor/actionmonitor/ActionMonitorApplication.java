package com.betvictor.actionmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.betvictor.actionmonitor.storage.MongoMessageRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = MongoMessageRepository.class)
public class ActionMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActionMonitorApplication.class, args);
	}

}
