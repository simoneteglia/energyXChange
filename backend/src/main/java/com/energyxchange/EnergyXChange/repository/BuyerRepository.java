package com.energyxchange.EnergyXChange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// Import the Buyer and JpaRepository classes
import com.energyxchange.EnergyXChange.model.Buyer;

import jakarta.transaction.Transactional;

// Enable JPA repositories and mark this interface as a repository
@EnableJpaRepositories
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {

    // Define method to find a Buyer by email and password
    List<Buyer> findByEmailAndPassword(String email, String password);

    // Define method to find a Buyer by email
    Buyer findByEmail(String email);    
    
    // Define method to find a Buyer by id
    Buyer findByid(int id);

    List<Buyer> findAll();

    //Query database for buyer with least energy
    @Query("SELECT b FROM Buyer b WHERE b.batteryCapacity = (SELECT MIN(b.batteryCapacity) FROM Buyer b)")
    public List<Buyer> findBuyersWithLeastEnergy();

    @Transactional
    @Modifying
    @Query("UPDATE Buyer s SET s.batteryCapacity = :batteryCapacity WHERE s.id = :id")
    void setBatteryCapacity(@Param("batteryCapacity") float battery_capacity, @Param("id") int id);

    @Query("SELECT b.batteryCapacity FROM Buyer b WHERE b.id = :id")
    float getBatteryCapacity(@Param("id") int id);

}
