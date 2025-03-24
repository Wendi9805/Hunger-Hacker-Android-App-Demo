package com.example.groupproject.searchFunction;

import android.util.Log;

import com.example.groupproject.loadDataFunction.FoodData;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @author Ke Wen
 */
public class StringExp extends Exp {
    private String value;

    public StringExp(String value) {
        this.value = value;
    }

    @Override
    public String show() {
        return value;
    }

    @Override
    public Set<FoodData> evaluate(Map<String, Set<FoodData>> data) {
        return data.values().stream()
                .flatMap(Set::stream) // Flatten all sets of FoodData
                .filter(foodData -> foodData.matchesCriteria(value))
                .collect(Collectors.toSet());
    }
}

