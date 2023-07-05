// Import necessary classes
package com.energyxchange.EnergyXChange.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energyxchange.EnergyXChange.repository.SellerRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/seller") // Map requests to /api/v1/seller to this controller
@CrossOrigin // Enable cross-origin requests
public class SellerController {

    private final SellerRepository sellerRepository;

    public SellerController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    //Get all data of a seller
    @GetMapping("/{id}")
    public ResponseEntity<?> getSeller(@PathVariable("id") int id) {
        return ResponseEntity.ok(sellerRepository.findById(id));
    }

    //Get battery capacity of a seller
    @GetMapping("/{id}/battery")
    public ResponseEntity<?> getBatteryCapacity(@PathVariable("id") int id) {
        return ResponseEntity.ok(sellerRepository.getBatteryCapacity(id));
    }

    //Get battery percentage of a seller
    @GetMapping("/{id}/batteryPercentage")
    public ResponseEntity<?> getBatteryPercentage(@PathVariable("id") int id) {
        return ResponseEntity.ok(sellerRepository.getBatteryCapacity(id)/10000);
    }

    // Add a new solar panel to the seller's inventory
    @PutMapping("/{id}/incrementPanels")
    @Transactional
    public ResponseEntity<?> addSolarPanel(@PathVariable("id") int id) {
        sellerRepository.addSolarPanel(id);
        return ResponseEntity.ok().build();
    }

    // Switches on the selling 
    @PutMapping("/{id}/startSelling")
    @Transactional
    public ResponseEntity<?> startSelling(@PathVariable("id") int id) {
        sellerRepository.startSelling(id);
        return ResponseEntity.ok().build();
    }

    // Switches off the selling
    @PutMapping("/{id}/stopSelling")
    @Transactional
    public ResponseEntity<?> stopSelling(@PathVariable("id") int id) {
        sellerRepository.stopSelling(id);
        return ResponseEntity.ok().build();
    }
    
}
