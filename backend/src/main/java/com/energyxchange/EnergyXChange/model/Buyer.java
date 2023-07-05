// Import required packages
package com.energyxchange.EnergyXChange.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Define the buyer entity
@Entity

// Use Lombok annotations for boilerplate code
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

// Map the entity to a table named "buyers"
@Table(name = "buyers")
public class Buyer implements UserDetails {

    // Define the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Define the entity attributes
    private String email;
    private String name;
    private String address;
    private String password;
    private Boolean status;
    private int batteryId;
    private float batteryCapacity;

    // Define the "role" attribute as an enumerated type
    @Enumerated(EnumType.STRING)
    private Role role;

    // Implement the UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public float getBatteryCapacity() {
        return batteryCapacity;
    }    
    
    public void setBatteryCapacity(float capacity) {
        batteryCapacity = capacity;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
