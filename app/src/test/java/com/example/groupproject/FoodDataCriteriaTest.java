package com.example.groupproject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.groupproject.loadDataFunction.FoodData;

import org.junit.Test;

/**
 * Test if the FoodDataCriteria works rightly
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class FoodDataCriteriaTest {
    @Test
    public void testMatchesCriteria() {
        // Setup
        FoodData foodData = new FoodData();
        foodData.productName = "Apple";
        foodData.provider = "Fresh Farms";
        foodData.address = "123 Orchard Lane";

        // Test matching criteria
        assertTrue(foodData.matchesCriteria("apple"), "Should return true as productName contains 'apple'");
        assertTrue(foodData.matchesCriteria("farms"), "Should return true as provider contains 'farms'");
        assertTrue(foodData.matchesCriteria("orchard"), "Should return true as address contains 'orchard'");

    }
}
