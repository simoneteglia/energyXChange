// Import necessary classes
package com.energyxchange.EnergyXChange.controller;
import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.energyxchange.EnergyXChange.model.Buyer;
import com.energyxchange.EnergyXChange.repository.BuyerRepository;

@RestController
@RequestMapping("/api/v1/buyer") // Map requests to /api/v1/buyer to this controller
@CrossOrigin
public class BuyerController {

    private final BuyerRepository buyerRepository;
    private final RestTemplate restTemplate;

    //constructor
     public BuyerController(BuyerRepository buyerRepository, RestTemplateBuilder restTemplateBuilder) {
        this.buyerRepository = buyerRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    String batteryPercentageURL = "http://localhost:8080/api/v1/seller/";

    //Get all data of a buyer
    @GetMapping("/{id}")
    public ResponseEntity<?> getSeller(@PathVariable("id") int id) {
        System.out.println("Received request for id = "+ id);
        return ResponseEntity.ok(buyerRepository.findById(id));
    }

    //Get battery capacity of a buyer
    @GetMapping("/{id}/battery")
    public ResponseEntity<?> getBatteryCapacity(@PathVariable("id") int id) {
        return ResponseEntity.ok(buyerRepository.getBatteryCapacity(id));
    }
    
    //Get battery percentage of a buyer
    @GetMapping("/{id}/batteryPercentage")
    public ResponseEntity<?> getBatteryPercentage(@PathVariable("id") int id) {
        return ResponseEntity.ok(buyerRepository.getBatteryCapacity(id)/10000);
    }

    //Method for incrementing battery capacity of input user 
    public void buyEnergy(int buyerID, float amountOfEnergy, int sellerID) {
        
        //Find the buyer with the right id
        Optional<Buyer> buyers = buyerRepository.findById(buyerID);
        Buyer buyer = buyers.get();

        //Increment its energy
        float currentCapacity = buyer.getBatteryCapacity();

        buyerRepository.setBatteryCapacity(currentCapacity + amountOfEnergy, buyerID);

        // Float batteryPercentage = restTemplate.getForObject(batteryPercentageURL + buyerID + "/batteryPercentage", Float.class);
        // if (batteryPercentage != null) {
        //     System.out.println("Percentage of battery of buyer: " + buyerID + " is: " + batteryPercentage*100 + "%");
        // }
        // else
        // {
        //     System.out.println("Error in getting battery percentage");
        // }
    }
    
    
}
