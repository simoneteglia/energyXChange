package com.energyxchange.EnergyXChange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energyxchange.EnergyXChange.model.Transaction;

import jakarta.transaction.Transactional;

// Enable JPA repositories and mark this interface as a repository
@EnableJpaRepositories
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    
    // Define method to find a trransaction by its id
    Transaction findByid(int id);

    List<Transaction> findAll();
    
    // Query method to find all transactions by the seller
    @Transactional
    @Query("SELECT t FROM Transaction t WHERE t.sellerID = :sellerID")
    List<Transaction> findBySeller(@Param("sellerID") int sellerID);

    // Query method to find all transactions by the buyer
    @Transactional
    @Query("SELECT t FROM Transaction t WHERE t.buyerID = :buyerID")
    List<Transaction> findByBuyer(@Param("buyerID") int buyerID);

    // Query method to sum the revenue from all transactions with seller id
    @Transactional
    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.sellerID = :sellerID")
    int getSellerRevenue(@Param("sellerID") int sellerID);

    // Query method to sum the energy sold from all transactions with seller id
    @Transactional
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.sellerID = :sellerID")
    int getSellerTotalEnergySold(@Param("sellerID") int sellerID);

    // Query method to sum the expenses from all transactions with buyer id
    @Transactional
    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.buyerID = :buyerID")
    int getBuyerTotalExpenses(@Param("buyerID") int buyerID);

    // Query method to sum the energy buyed from all transactions with buyer id
    @Transactional
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.buyerID = :buyerID")
    int getBuyerTotalEnergyBuyed(@Param("buyerID") int buyerID);

    // Query method to add a transaction
    @Modifying
    @Transactional
    @Query("INSERT INTO Transaction (timestamp, amount, price, buyerID, sellerID) \n" +
                    "VALUES (:timestamp, :amount, :price, :buyerID, :sellerID)")
    void addTransaction(@Param("timestamp") int timestamp, @Param("amount") float amount, @Param("price") int price, 
                        @Param("buyerID") int buyerID, @Param("sellerID") int sellerID);

    //Query method to modify the id of the buyer given timestamp and sellerID
    @Modifying 
    @Transactional
    @Query("UPDATE Transaction t SET t.buyerID = :buyerID " +
            "WHERE t.timestamp = :timestamp AND t.sellerID = :sellerID AND t.amount = :amount")
    void modifyBuyerID( @Param("timestamp") int timestamp, @Param("buyerID") int buyerID, 
                        @Param("sellerID") int sellerID, @Param("amount") float amount);
}
