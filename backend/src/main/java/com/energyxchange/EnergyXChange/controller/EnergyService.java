package com.energyxchange.EnergyXChange.controller;

import java.util.Random;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.energyxchange.EnergyXChange.model.Buyer;
import com.energyxchange.EnergyXChange.model.Seller;
import com.energyxchange.EnergyXChange.model.Weather;
import com.energyxchange.EnergyXChange.repository.BuyerRepository;
import com.energyxchange.EnergyXChange.repository.SellerRepository;

@RestController // Declares that this class is a REST controller
@RequestMapping("/api/v1") // Maps all methods in this class to /api/v1
@CrossOrigin
public class EnergyService {

    private final RestTemplate restTemplate;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    
    public EnergyService(RestTemplateBuilder restTemplateBuilder,
                        BuyerRepository buyerRepository, SellerRepository sellerRepository)
    {
        this.restTemplate = restTemplateBuilder.build();
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
    }

    @GetMapping("/user/sellerOrBuyer/{email}")// Maps this method to GET /api/v1/user/sellerOrBuyer/{email}
    public ResponseEntity<String> sellerOrBuyer(@PathVariable String email) {
        
        Seller seller = sellerRepository.findByEmail(email);

        if (seller != null) {
            return ResponseEntity.ok("Seller");
        }

        Buyer buyer = buyerRepository.findByEmail(email);

        if (buyer != null) {
            return ResponseEntity.ok("Buyer");
        }

        return (ResponseEntity<String>) ResponseEntity.notFound();
    }

    @GetMapping("/user/{email}")// Maps this method to GET /api/v1/user/{email}
    public ResponseEntity<?> getUser(@PathVariable String email) {
        
        Seller seller = sellerRepository.findByEmail(email);

        if (seller != null) {
            return ResponseEntity.ok(seller.getId());
        }

        Buyer buyer = buyerRepository.findByEmail(email);

        if (buyer != null) {
            System.out.println(buyer);
            return ResponseEntity.ok(buyer.getId());
        }

        return ResponseEntity.ok(buyerRepository.findByEmail(email).toString());
    }

    @GetMapping("/energy/produced") // Maps this method to GET /api/v1/energy/produced
    public ResponseEntity<Double> getEnergyProduced() {

        //get weather
        String weatherUrl = "http://localhost:8080/api/v1/weather";
        Weather currentWeather =  this.restTemplate.getForObject(weatherUrl, Weather.class);
        
        //get time
        String timeUrl = "http://localhost:8080/api/v1/time";
        int currentTime = this.restTemplate.getForObject(timeUrl, Integer.class);

        // Returns the energy produced as a ResponseEntity
        return ResponseEntity.ok(
                calculateEnergyProduced(
                    currentTime, 
                    currentWeather.getTemperature(), 
                    currentWeather.getCondition(), 
                    currentWeather.getSunlightIntensity()
                )
            ); 
    }

    @GetMapping("/energy/consumed") // Maps this method to GET /api/v1/energy/consumed
    public ResponseEntity<Double> getEnergyConsumed() {

        //get weather
        String weatherUrl = "http://localhost:8080/api/v1/weather";
        Weather currentWeather =  this.restTemplate.getForObject(weatherUrl, Weather.class);
        // System.out.println("Current weather: " +  currentWeather.getCondition());
        
        //get time
        String timeUrl = "http://localhost:8080/api/v1/time";
        int currentTime = this.restTemplate.getForObject(timeUrl, Integer.class);
        // System.out.println("Current time: " +  currentTime);

        // Returns the energy consumed as a ResponseEntity
        return ResponseEntity.ok(
                calculateEnergyConsumed(
                    currentTime, 
                    currentWeather.getTemperature()
                )
            ); 
    }

    // Calculate the energy produced based on the hour of the day, the temperature and the weather conditions
    public double calculateEnergyProduced(int hour, int temperature, String weatherCondition, int sunlightIntensity) {

        // Adjust the energy produced based on the hour of the day
        double baseEnergy = getBaseEnergyProduction(hour);

        // Adjust the energy produced based on the temperature
        double temperatureFactor = getTemperatureFactor(temperature);

        // Adjust the energy produced based on the weather conditions
        double weatherConditionFactor = getWeatherConditionFactor(weatherCondition);

        // Adjust the energy produced based on the sunlight conditions
        double sunlightConditionFactor = getSunlightConditionFactor(sunlightIntensity);

        Random random = new Random();

        float randomFloat = random.nextFloat() * 0.5f + 0.5f;

        double adjustedEnergy = baseEnergy * sunlightConditionFactor * temperatureFactor * weatherConditionFactor * randomFloat;

        return adjustedEnergy;
    }

    // Calculate the energy produced based on the hour of the day, the temperature and the weather conditions
    public double calculateEnergyConsumed(int hour, int temperature) {

        // Adjust the energy produced based on the hour of the day
        double baseEnergy = getBaseEnergyConsumption(hour);

        // Adjust the energy produced based on the temperature
        double temperatureFactor = getTemperatureFactor(temperature);

        Random random = new Random();

        float randomFloat = random.nextFloat() * 0.5f + 0.5f;
       
        double adjustedEnergy = baseEnergy * temperatureFactor * randomFloat;

        return adjustedEnergy;
    }

    private double getSunlightConditionFactor(int sunlightIntensity) {
        
        double sunlightConditionFactor = 4 * sunlightIntensity / 1000;

        return sunlightConditionFactor;

    }

    private double getTemperatureFactor(int temperature) {

        double temperatureFactor;

        if (temperature >= 10) {
            temperatureFactor = (double) (temperature ) / 10.0;
        }

        else if (temperature >= -10 && temperature < 10) {
            temperatureFactor = (double) 1.0;
        }

        else {
            temperatureFactor = (double) .5;
        }

        return temperatureFactor;
    }

    private double getBaseEnergyProduction(int hour) {

        double baseEnergy;

        // Medium energy production
        if ((hour >= 6 && hour < 9) || (hour >= 15 && hour < 18)) {
            baseEnergy = 1000.0;
        }

        //Daytime, maximum energy production
        else if (hour >= 9 && hour < 15) {
            baseEnergy = 1000.0;
        }

        //Nighttime, minimum energy production
        else  {
            baseEnergy = 100.0;
        }

        return baseEnergy;

    }

    private double getBaseEnergyConsumption(int hour) {

        double baseEnergy;

        // Maximum energy consumption
        if ((hour >= 6 && hour < 9) || (hour >= 18 && hour < 24)) {
            baseEnergy = 1000.0;
        }

        //Daytime, medium energy production
        else if (hour >= 9 && hour < 18) {
            baseEnergy = 500.0;
        }

        //Nighttime, minimum energy production
        else  {
            baseEnergy = 100.0;
        }

        return baseEnergy;

    }

    private double getWeatherConditionFactor(String weatherCondition) {

        double weatherConditionFactor;

        if (weatherCondition == "Sunny") {
            weatherConditionFactor = 3.0;
        }
        if (weatherCondition == "Cloudy") {
            weatherConditionFactor = 0.8;
        }
        else {
            weatherConditionFactor = 0.2;
        }

        return weatherConditionFactor;

    }

}

