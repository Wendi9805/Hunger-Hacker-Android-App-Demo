package com.example.groupproject.loadDataFunction;

import java.io.Serializable;

/**
 * This Class is the instance of our FoodData,
 * it contains all the attributes we need,
 * all the attributes also match the names in firebase database
 *
 * @author ${Wendi Fan}
 */

public class FoodData implements Serializable {
    public String productName;
    public String category;
    public String date;
    public String expiration;
    public String provider;
    public String address;
    public String offer;
    public String eventName;
    public String phoneNumber;


    public String state;

    //The methods below are used to get values we need
    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getProvider() {
        return provider;
    }

    public String getAddress() {
        return address;
    }

    public String getOffer() {
        return offer;
    }

    public String getEventName() {
        return eventName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getState() {
        return state;
    }

    // Checks whether any field of FoodData contains the specified search string (case insensitive)
    public boolean matchesCriteria(String value) {
        // Convert to lowercase for case-insensitive comparisons
        String lowerCaseValue = value.toLowerCase();
        return (productName != null && productName.toLowerCase().contains(lowerCaseValue)) ||
                (provider != null && provider.toLowerCase().contains(lowerCaseValue)) ||
                (address != null && address.toLowerCase().contains(lowerCaseValue));
    }
}
