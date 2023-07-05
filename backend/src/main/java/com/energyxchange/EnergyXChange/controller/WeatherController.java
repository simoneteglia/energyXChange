// Import necessary classes
package com.energyxchange.EnergyXChange.controller;
import com.energyxchange.EnergyXChange.model.Weather;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// Declare the class as a RestController
@RestController
@RequestMapping("/api/v1") // Map requests to /api/v1/seller to this controller
@CrossOrigin // Enable cross-origin requests
public class WeatherController {

    Weather currentWeather;

    public WeatherController() {
        this.currentWeather = getRandomWeather();
    }

    // Create a list of weather objects
    private List<Weather> weatherList = Arrays.asList(
            new Weather("Sunny", 75, 1000),
            new Weather("Cloudy", 65, 500),
            new Weather("Rainy", 55, 100)
    );

    @GetMapping("/weather")
    public Weather getCurrentWeather() {
        return currentWeather;
    }

    // Return a randomly chosen Weather object from the list
    public Weather getRandomWeather() {
        Random rand = new Random();
        int index = rand.nextInt(weatherList.size());
        return weatherList.get(index);
    }

    @Scheduled(fixedDelay = 15000) // Schedules this method to be executed every 15 seconds
    public void computeNewWeather() {
        this.currentWeather = getRandomWeather();
    }

}
