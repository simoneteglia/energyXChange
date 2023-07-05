package com.energyxchange.EnergyXChange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energyxchange.EnergyXChange.RabbitMQ.QueueSender;

@RestController
@RequestMapping("/api/v1")
public class EnergyPacketPublisher {
    @Autowired
    private QueueSender queueSender;

    @PostMapping("/energyPacket")
    public String send(@RequestBody String message){
        // final var customMessage = new CustomMessage("Hello EnergyXChange!");
        queueSender.send(message);
        return "OK";
    }
}
