// Specifies the package where the class is located
package com.energyxchange.EnergyXChange.auth;

// Import Lombok annotations
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Add Lombok annotations to the class
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

// Define the class AuthenticationResponse
public class AuthenticationResponse {
    // Define the class attribute "token"
    private String token;
}
