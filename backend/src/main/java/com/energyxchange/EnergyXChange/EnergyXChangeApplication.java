package com.energyxchange.EnergyXChange;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@EnableRabbit
@SpringBootApplication
public class EnergyXChangeApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnergyXChangeApplication.class, args);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		// Create a new ConcurrentTaskScheduler
		return new ConcurrentTaskScheduler();
	}

}
