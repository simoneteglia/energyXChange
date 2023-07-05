// Importing the Lombok annotations
package com.energyxchange.EnergyXChange.auth;

// Lombok annotations to generate boilerplate code
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotation to generate getters, setters, and other boilerplate code
@Data

// Annotation to generate a builder pattern for object creation
@Builder

// Annotation to generate a constructor with all arguments
@AllArgsConstructor

// Annotation to generate a no-argument constructor
@NoArgsConstructor
public class AuthenticationRequest {

    // Fields of the AuthenticationRequest class
    private String email;
    private String password;
}
