package com.energyxchange.EnergyXChange.EnergySchedulers;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.energyxchange.EnergyXChange.model.Buyer;
import com.energyxchange.EnergyXChange.model.Seller;
import com.energyxchange.EnergyXChange.repository.BuyerRepository;
import com.energyxchange.EnergyXChange.repository.SellerRepository;

@Configuration
@EnableScheduling
public class EnergyConsumptionScheduler {
    private final BuyerRepository buyerRepository;
    private final RestTemplate restTemplate;
    private final SellerRepository sellerRepository;

    public EnergyConsumptionScheduler(SellerRepository sellerRepository, BuyerRepository buyerRepository, RestTemplateBuilder restTemplateBuilder) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Scheduled(fixedDelay = 5000)
    public void simulateEnergy(){

        String energyConsumedURL = "http://localhost:8080/api/v1/energy/consumed";

        //get all buyers
        List<Buyer> buyers = buyerRepository.findAll();

        //decrement energy of buyers based on consumption
        buyers.forEach(buyer -> {

            float currentCapacity = buyer.getBatteryCapacity();
            float decrement = this.restTemplate.getForObject(energyConsumedURL, Float.class);

            if (currentCapacity - decrement < 0) 
            {
                buyerRepository.setBatteryCapacity(0, buyer.getId());
            }
            else
            {
                buyerRepository.setBatteryCapacity(currentCapacity - decrement, buyer.getId());
            }
            
        });

        //get all sellers
        List<Seller> sellers = sellerRepository.findAll();

        //decrement energy of sellers based on consumption
        sellers.forEach(seller -> {

            float currentCapacity = seller.getBatteryCapacity();
            float decrement = this.restTemplate.getForObject(energyConsumedURL, Float.class);

            if (currentCapacity - decrement/10 < 0) 
            {
                sellerRepository.setBatteryCapacity(0, seller.getId());
            }
            else
            {
                sellerRepository.setBatteryCapacity(currentCapacity - decrement/10, seller.getId());
            }
            
        });
    }

}
