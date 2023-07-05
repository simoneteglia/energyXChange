package com.energyxchange.EnergyXChange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energyxchange.EnergyXChange.model.Seller;

import jakarta.transaction.Transactional;

// Enable JPA repositories and mark this interface as a repository
@EnableJpaRepositories
@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    // Define method to find a Seller by email and password
    List<Seller> findByEmailAndPassword(String email, String password);

    // Define method to find a Seller by email
    Seller findByEmail(String email);    
    
    // Define method to find a Seller by id
    Seller findByid(int id);

    List<Seller> findAll();
    
    @Transactional
    @Modifying
    @Query("UPDATE Seller s SET s.panels = s.panels + 1 WHERE s.id = :id")
    void addSolarPanel(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Seller s SET s.batteryCapacity = :batteryCapacity WHERE s.id = :id")
    void setBatteryCapacity(@Param("batteryCapacity") float battery_capacity, @Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Seller s SET s.status = 0 WHERE s.id = :id")
    void stopSelling(@Param("id") int id);
    
    @Transactional
    @Modifying
    @Query("UPDATE Seller s SET s.status = 1 WHERE s.id = :id")
    void startSelling(@Param("id") int id);

    @Query("SELECT s.batteryCapacity FROM Seller s WHERE s.id = :id")
    float getBatteryCapacity(@Param("id") int id);

}
