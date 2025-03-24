package com.example.groupproject;


import com.example.groupproject.followFunctions.FollowFunctions;
import com.example.groupproject.loadDataFunction.FoodData;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class FollowFunctionsTest {

    @Test
    public void sortFoodDataTest(){
        ArrayList<FoodData> foodData = new ArrayList<>();
        ArrayList<FoodData> testData = new ArrayList<>();
        ArrayList<String> followList = new ArrayList<>();

        followList.add("Coles");

        FoodData food1 = new FoodData();
        food1.productName = "Chicken Crimpies";
        food1.category = "Biscuit";
        food1.date = "2024-05-18";
        food1.expiration = "2024-05-20";
        food1.provider = "Coles";
        food1.address = "Around the corner";
        food1.offer = "Free";
        food1.eventName = "Best Snacks";
        food1.phoneNumber = "0412345678";

        FoodData food2 = new FoodData();
        food2.productName = "Barbecue Shapes";
        food2.category = "Biscuit";
        food2.date = "2024-05-18";
        food2.expiration = "2024-05-20";
        food2.provider = "Woolworths";
        food2.address = "Around the other corner";
        food2.offer = "Free";
        food2.eventName = "Classic Snacks";
        food2.phoneNumber = "0412345678";

        FoodData food3 = new FoodData();
        food3.productName = "Pizza Shapes";
        food3.category = "Biscuit";
        food3.date = "2024-05-18";
        food3.expiration = "2024-05-20";
        food3.provider = "Coles";
        food3.address = "Across the street";
        food3.offer = "Free";
        food3.eventName = "Respectable Choices";
        food3.phoneNumber = "0412345678";

        foodData.add(food1);
        foodData.add(food2);
        foodData.add(food3);

        testData.add(food1);
        testData.add(food3);
        testData.add(food2);

        ArrayList<FoodData> sortedFoodData = FollowFunctions.sortFoodData(foodData,followList);

        Assert.assertEquals(sortedFoodData,testData);
    }
}
