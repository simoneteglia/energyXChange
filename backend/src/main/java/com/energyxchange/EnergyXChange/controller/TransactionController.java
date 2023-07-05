// Import necessary classes
package com.energyxchange.EnergyXChange.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energyxchange.EnergyXChange.model.Transaction;
import com.energyxchange.EnergyXChange.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/transactions") // Map requests to /api/v1 to this controller
@CrossOrigin // Enable cross-origin requests
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Get all transactions
    @GetMapping("/getAll")
    @Transactional
    public ResponseEntity<?> viewTransactions() {
        List<Transaction> transactions = this.transactionRepository.findAll();
        return  ResponseEntity.ok(transactions);
    }

    // Get all transactions with seller id
    @GetMapping("/getBySeller")
    @Transactional
    public ResponseEntity<?> getBySeller(@RequestBody int sellerID) {
        List<Transaction> transactions = this.transactionRepository.findBySeller(sellerID);
        return  ResponseEntity.ok(transactions);
    }

    // Get all transactions with buyer id
    @GetMapping("/getByBuyer")
    @Transactional
    public ResponseEntity<?> getByBuyer(@RequestBody int buyerID) {
        List<Transaction> transactions = this.transactionRepository.findByBuyer(buyerID);
        return  ResponseEntity.ok(transactions);
    }

    // Sum the revenue from all transactions with seller id
    @GetMapping("/getSellerRevenue")
    @Transactional
    public ResponseEntity<?> getSellerRevenue(@RequestBody int sellerID) {
        double revenue = this.transactionRepository.getSellerRevenue(sellerID);
        return  ResponseEntity.ok(revenue);
    }    

    //sum the expenses from all transactions with buyer id
    @GetMapping("/getBuyerTotalExpenses")
    @Transactional
    public ResponseEntity<?> getBuyerExpenses(@RequestBody int buyerID) {
        double expenses = this.transactionRepository.getBuyerTotalExpenses(buyerID);
        return  ResponseEntity.ok(expenses);
    }
    
    //sum the energy buyed from all transactions with buyer id
    @GetMapping("/getBuyerTotalEnergyBuyed")
    @Transactional
    public ResponseEntity<?> getBuyerEnergy(@RequestBody int buyerID) {
        double energy = this.transactionRepository.getBuyerTotalEnergyBuyed(buyerID);
        return  ResponseEntity.ok(energy);
    }

    // sum the energy sold from all transactions with seller id
    @GetMapping("/getSellerTotalEnergySold")
    @Transactional
    public ResponseEntity<?> getSellerEnergy(@RequestBody int sellerID) {
        double energy = this.transactionRepository.getSellerTotalEnergySold(sellerID);
        return  ResponseEntity.ok(energy);
    }

    // Add a transaction
    @PostMapping("/add")
    @Transactional
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    
}
