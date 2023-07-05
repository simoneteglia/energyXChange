// Package declaration for the RegisterRequest class
package com.energyxchange.EnergyXChange.auth;

// Lombok annotations to generate boilerplate code for getters, setters, constructors, and builder methods
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data object representing a registration request
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyerRegisterRequest {
    // Fields representing the data to be included in the registration request
    private String email;
    private String name;
    private String address;
    private String password;
    private Boolean status;
    private int batteryId;
}