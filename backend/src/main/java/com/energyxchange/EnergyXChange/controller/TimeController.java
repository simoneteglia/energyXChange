package com.energyxchange.EnergyXChange.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController // Declares that this class is a REST controller
@RequestMapping("/api/v1") // Maps all methods in this class to /api
public class TimeController {

    Random random = new Random(); // Creates a new instance of the Random class
    private int currentTime = random.nextInt(24); // Initializes currentTime with a random integer between 0 and 23 (inclusive)

    @GetMapping("/time") // Maps this method to GET /api/time
    public ResponseEntity<Integer> getHour() {
        return ResponseEntity.ok(currentTime); // Returns the current time as a ResponseEntity
    }

    @Scheduled(fixedDelay = 5000) // Schedules this method to be executed every 5 seconds
    public void incrementHour() {
        currentTime = (currentTime + 1) % 24; // Increments the current time by 1 and wraps it around to 0 if it reaches 24
        System.out.println("\nCurrent time: " + currentTime); // Prints the current time to the console
    }
}
