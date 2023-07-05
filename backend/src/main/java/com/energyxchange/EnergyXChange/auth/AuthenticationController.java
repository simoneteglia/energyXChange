// Import required classes and libraries
package com.energyxchange.EnergyXChange.auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Mark class as REST controller with base URL
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // Handle POST requests to register a buyer
    @PostMapping("/register/buyer")
    public ResponseEntity<AuthenticationResponse> registerBuyer(@RequestBody BuyerRegisterRequest request){
        // Return HTTP response with registered user's authentication details
        return ResponseEntity.ok(authenticationService.registerBuyer(request));
    }

    // Handle POST requests to register a seller
    @PostMapping("/register/seller")
    public ResponseEntity<AuthenticationResponse> registerSeller(@RequestBody SellerRegisterRequest request){
        // Return HTTP response with registered user's authentication details
        return ResponseEntity.ok(authenticationService.registerSeller(request));
    }

    // Handle POST requests to authenticate a user
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        // Return HTTP response with authenticated user's details
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
