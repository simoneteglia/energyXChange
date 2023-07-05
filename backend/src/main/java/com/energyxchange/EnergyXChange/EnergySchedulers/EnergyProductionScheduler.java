package com.energyxchange.EnergyXChange.EnergySchedulers;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.energyxchange.EnergyXChange.RabbitMQ.QueueSender;
import com.energyxchange.EnergyXChange.controller.TimeController;
import com.energyxchange.EnergyXChange.model.Seller;
import com.energyxchange.EnergyXChange.repository.SellerRepository;
import com.energyxchange.EnergyXChange.repository.TransactionRepository;

@Configuration
@EnableScheduling
public class EnergyProductionScheduler {
    private final SellerRepository sellerRepository;
    private final TransactionRepository transactionsRepository;
    private final QueueSender queueSender;
    private final RestTemplate restTemplate;
    private TimeController timeController;

    public EnergyProductionScheduler(TimeController timeController, SellerRepository sellerRepository, TransactionRepository transactionsRepository,
                 QueueSender queueSender, RestTemplateBuilder restTemplateBuilder) {
        this.transactionsRepository = transactionsRepository;
        this.sellerRepository = sellerRepository;
        this.timeController = timeController;
        this.queueSender = queueSender;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Scheduled(fixedDelay = 5000)
    public void simulateEnergy(){

        String energyProducedURL = "http://localhost:8080/api/v1/energy/produced";
        String batteryPercentageURL = "http://localhost:8080/api/v1/seller/";

        //get all sellers
        List<Seller> sellers = sellerRepository.findAll();

        //increment energy produced based on weather and solar panels owned
        sellers.forEach(seller -> {
            
            float currentCapacity = seller.getBatteryCapacity();
            
            float increment = this.restTemplate.getForObject(energyProducedURL, Float.class);
            
            //multiply by number of solar panels owned
            increment = increment * seller.getSolarPanelsOwned();

            sellerRepository.setBatteryCapacity(currentCapacity + increment, seller.getId());
            
            float amountOfExceedingEnergy = 1 + seller.getBatteryCapacity() - seller.getThreshold();

            
            //if he's not selling skip him
            if (seller.getStatus() == 1 && amountOfExceedingEnergy > 0) {


                int sellerId = seller.getId();
                int currentTime = timeController.getHour().getBody();

                queueSender.send(String.valueOf(sellerId) + ", " + String.valueOf(currentTime) + ", " +
                                String.valueOf(amountOfExceedingEnergy));
                                
                sellerRepository.setBatteryCapacity(seller.getBatteryCapacity() - amountOfExceedingEnergy, seller.getId());
        
                // Float batteryPercentage = this.restTemplate.getForObject(batteryPercentageURL + sellerId + "/batteryPercentage", Float.class);
                // if (batteryPercentage != null) {
                //     System.out.println("Percentage of battery of seller: " + sellerId + " is: " + batteryPercentage*100 + "%");
                // }
                // else
                // {
                //     System.out.println("Error in getting battery percentage");
                // }
            }
        });
    }

}
