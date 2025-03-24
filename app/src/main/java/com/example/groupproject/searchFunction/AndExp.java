package com.example.groupproject.searchFunction;

import android.util.Log;

import com.example.groupproject.loadDataFunction.FoodData;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @author Ke Wen
 * @studentId u7588635
 */
public class AndExp extends Exp {
    private Exp left;
    private Exp right;

    public AndExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String show() {
        return "(" + left.show() + " AND " + right.show() + ")";
    }

    @Override
    public Set<FoodData> evaluate(Map<String, Set<FoodData>> data) {
        Set<FoodData> leftResults = left.evaluate(data);
        Set<FoodData> rightResults = right.evaluate(data);
        return leftResults.stream()
                .filter(rightResults::contains)
                .collect(Collectors.toSet());
    }
}

