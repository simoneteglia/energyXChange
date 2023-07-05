// Declare package for this file
package com.energyxchange.EnergyXChange.auth;

// Import necessary packages and classes
import com.energyxchange.EnergyXChange.config.JwtService;
import com.energyxchange.EnergyXChange.model.Buyer;
import com.energyxchange.EnergyXChange.model.Role;
import com.energyxchange.EnergyXChange.model.Seller;
import com.energyxchange.EnergyXChange.repository.BuyerRepository;
import com.energyxchange.EnergyXChange.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

// Declare this class as a service and force the initialization of the final variables
@Service
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationService {

    // Declare final variables for repositories, encoders, services and managers
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Method to register a new user
    public AuthenticationResponse registerSeller(SellerRegisterRequest request) {

        // Create a new user object using the input request
        var user = Seller.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .address(request.getAddress())
                .status(request.getStatus())
                .batteryId(request.getBatteryId())
                .threshold(request.getThreshold())
                .build();

        // Save the user in the repository
        sellerRepository.save(user);

        // Generate a JWT token for the user
        var jwtToken = jwtService.generateToken(user);

        // Return an authentication response containing the JWT token
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    // Method to register a new user
    public AuthenticationResponse registerBuyer(BuyerRegisterRequest request) {

        // Create a new user object using the input request
        var user = Buyer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .address(request.getAddress())
                .status(request.getStatus())
                .batteryId(request.getBatteryId())
                .build();

        // Save the user in the repository
        buyerRepository.save(user);

        // Generate a JWT token for the user
        var jwtToken = jwtService.generateToken(user);

        // Return an authentication response containing the JWT token
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    // Method to authenticate an existing user
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        // Authenticate the user using the authentication manager and their email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Find the user in the repository using their email
        System.out.println("Cerco un utente");
        Seller seller = sellerRepository.findByEmail(request.getEmail());
        Buyer buyer = buyerRepository.findByEmail(request.getEmail());
        
        if (seller != null) {
            // Generate a JWT token for the user
            var jwtToken = jwtService.generateToken((UserDetails) seller);
            
            // Return an authentication response containing the JWT token
            return AuthenticationResponse.builder().token(jwtToken).build();
        }
        else if (buyer != null){
            // Generate a JWT token for the user
            var jwtToken = jwtService.generateToken((UserDetails) buyer);

            // Return an authentication response containing the JWT token
            return AuthenticationResponse.builder().token(jwtToken).build();
        }

        // Return an authentication response containing the JWT token
         return AuthenticationResponse.builder().token("null").build();
    
    }

}
