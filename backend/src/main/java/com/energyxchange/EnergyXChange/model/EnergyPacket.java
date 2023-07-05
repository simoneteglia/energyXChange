package com.energyxchange.EnergyXChange.model;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;

// Energy Packet
public class EnergyPacket implements Serializable {
    private String id;
    private String producer;
    private double amount;
    private double price;

    public EnergyPacket(String id, String producer, double amount, double price) {
        this.id = id;
        this.producer = producer;
        this.amount = amount;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "EnergyPacket{" +
                "id='" + id + '\'' +
                ", producer='" + producer + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    public static EnergyPacket fromMessage(Message message) throws JMSException {
        ObjectMessage objectMessage = (ObjectMessage) message;
        return (EnergyPacket) objectMessage.getObject();
    }

    public Message toMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(this);
        return objectMessage;
    }
}