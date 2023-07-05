package com.energyxchange.EnergyXChange.RabbitMQ;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomMessage(@JsonProperty("message") String message) implements Serializable {

    float getAmountOfEnergy() {
        // System.out.println("La quantità di energia è: " + message.split(", ")[1]);
        return Float.parseFloat(message.split(", ")[2]);

    }

    public int getSellerID() {
        return Integer.parseInt(message.split(", ")[0].split("\\.")[0]);
    }

    public int getTimestamp() {
        return Integer.parseInt(message.split(", ")[1].split("\\.")[0]);
    }

}
