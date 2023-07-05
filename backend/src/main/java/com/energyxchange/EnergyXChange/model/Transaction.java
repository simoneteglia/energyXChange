package com.energyxchange.EnergyXChange.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Define the Seller entity
@Entity

// Use Lombok annotations for boilerplate code
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

// Map the entity to a table named "buyers"
@Table(name = "transaction")
public class Transaction {
    
    // Define the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private int timestamp;
    private int amount;
    private int price;
    private int buyerID;
    private int sellerID;

    // Getters and setters for id, timestamp, amount, price, buyerID and sellerID

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public int getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }

}