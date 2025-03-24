package com.example.groupproject.searchFunction;

import com.example.groupproject.loadDataFunction.FoodData;

import java.util.Map;
import java.util.Set;
/**
 * @author Ke Wen
 */
public abstract class Exp {
    public abstract String show();
    public abstract Set<FoodData> evaluate(Map<String, Set<FoodData>> data);
}
