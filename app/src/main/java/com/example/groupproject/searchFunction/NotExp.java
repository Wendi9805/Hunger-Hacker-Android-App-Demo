package com.example.groupproject.searchFunction;

import com.example.groupproject.loadDataFunction.FoodData;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @author Ke Wen
 */
public class NotExp extends Exp {
    private Exp left;
    private Exp right;

    public NotExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String show() {
        return "(" + left.show() + " NOT " + right.show() + ")";
    }

    @Override
    public Set<FoodData> evaluate(Map<String, Set<FoodData>> data) {
        Set<FoodData> leftResults = left.evaluate(data);
        Set<FoodData> rightResults = right.evaluate(data);
        return leftResults.stream()
                .filter(item -> !rightResults.contains(item))
                .collect(Collectors.toSet());
    }
}

