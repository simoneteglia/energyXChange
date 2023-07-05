// Define a package for the class
package com.energyxchange.EnergyXChange.model;

// Define a class called Weather
public class Weather {

    // Declare two private instance variables for the weather condition and temperature
    private String condition;
    private int temperature;

    private int sunlightIntensity;

    // Create a constructor that takes the weather condition and temperature as arguments
    public Weather(String condition, int temperature, int sunlightIntensity) {
        // Initialize the instance variables with the constructor arguments
        this.condition = condition;
        this.temperature = temperature;
        this.sunlightIntensity = sunlightIntensity;
    }

    public int getSunlightIntensity() {
        return sunlightIntensity;
    }

    public void setSunlightIntensity(int sunlightIntensity) {
        this.sunlightIntensity = sunlightIntensity;
    }

    // Create a getter method for the condition variable
    public String getCondition() {
        return condition;
    }

    // Create a setter method for the condition variable
    public void setCondition(String condition) {
        this.condition = condition;
    }

    // Create a getter method for the temperature variable
    public int getTemperature() {
        return temperature;
    }

    // Create a setter method for the temperature variable
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

}
