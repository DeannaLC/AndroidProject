package edu.vassar.cmpu203.high_noon_heist.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void randPlayer() {
    }

    @Test
    void getValue() {

        Location bank = new Location();
        int bankVal = bank.getValue("bank");
        System.out.println(bankVal);
        assertTrue(bankVal >= 1500 && bankVal <= 2000);

        Location saloon = new Location();
        int saloonVal = bank.getValue("saloon");
        System.out.println(saloonVal);
        assertTrue(saloonVal >= 1000 && saloonVal <= 1500);

        Location ranch = new Location();
        int ranchVal = ranch.getValue("ranch");
        System.out.println(ranchVal);
        assertTrue(ranchVal >= 500 && ranchVal <= 1000);


    }
}