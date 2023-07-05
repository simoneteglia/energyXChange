package com.energyxchange.EnergyXChange.RabbitMQ;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import com.energyxchange.EnergyXChange.controller.BuyerController;
import com.energyxchange.EnergyXChange.model.Buyer;
import com.energyxchange.EnergyXChange.repository.BuyerRepository;
import com.energyxchange.EnergyXChange.repository.TransactionRepository;

import java.util.List;
import java.util.Random;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

@Component
public class QueueConsumer {

    private final BuyerRepository buyerRepository;
    private final BuyerController buyerController;
    private TransactionRepository transactionRepository;

    public QueueConsumer(TransactionRepository transactionRepository, BuyerRepository buyerRepository, BuyerController buyerController) {
        this.buyerRepository = buyerRepository;
        this.buyerController = buyerController;
        this.transactionRepository = transactionRepository;
    }

    //TODO: rimuovere?
    public Jackson2JsonMessageConverter consumerJsonConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload CustomMessage fileBody) {

        //Select an user from the database
        List<Buyer> leastBatteryBuyers = buyerRepository.findBuyersWithLeastEnergy();

        Random rand = new Random();
        Buyer leastBatteryBuyer = leastBatteryBuyers.get(rand.nextInt(leastBatteryBuyers.size()));

        // Call BuyerController method to increment battery capacity
        buyerController.buyEnergy(leastBatteryBuyer.getId(), fileBody.getAmountOfEnergy(), fileBody.getSellerID());

        System.out.println("Sold " + String.valueOf(fileBody.getAmountOfEnergy()) + "energy from seller " 
                + String.valueOf(fileBody.getSellerID()) + " to buyer " + String.valueOf(leastBatteryBuyer.getId()));

        double price = fileBody.getAmountOfEnergy() * 0.15;

        //round price to int
        int rounded_price = (int) Math.ceil(price);

        //add transaction
        transactionRepository.addTransaction(  fileBody.getTimestamp(), fileBody.getAmountOfEnergy(), 
                                                rounded_price, leastBatteryBuyer.getId(), fileBody.getSellerID());
                        
    }



}
