package com.example.groupproject.searchFunction;

import static okhttp3.internal.Internal.instance;

import androidx.annotation.NonNull;

import com.example.groupproject.loadDataFunction.FoodData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Ke Wen
 * @studentId u7588635
 */
public class ProductSearchIndex {

    private static ProductSearchIndex instance;
    private static DatabaseReference mDatabase;
    private RedBlackTree dataTree;
    private List<FoodData> allFoodData = new ArrayList<>();

    public ProductSearchIndex() {
        this.dataTree = new RedBlackTree();
        initializeDatabase();
    }

    /**
     * @author Ke Wen
     * @studentId u7588635
     * Detected Code Smell on code committed before 1st May, 2024
     * Refactored it when finalizing the code.
     */

    /**
     * CODE SMELL EXPLANATION IN DETAIL
     *
     * TYPE OF CODE SMELL THAT WAS DETECTED:- “Singleton Not Used”
     *
     * What it is?
     * The "Singleton Not Used" code smell occurs when a class that logically should be instantiated once is instead
     * instantiated multiple times. This often leads to inconsistent states across different components of the
     * application, complicating state management and increasing the likelihood of data errors.
     *
     *
     *  DETAILED EXPLANATION:-
     * During the initial development phase of the search function, each click on the search button triggered a
     * data reload from Firebase. At that point, since our UI components were not integrated and the search function
     * operated independently, no immediate issues were apparent. However, post UI integration, a significant
     * problem arose: the search function only worked correctly the first time a user entered the app. Subsequent
     * navigations to other pages and returns to the search page resulted in an empty search results list.
     * The underlying issue was that each navigation required reinitialization of the data index due to the absence
     * of a singleton pattern, which led to the loss of previously loaded data.
     *
     * To address this problem, I modified the `ProductSearchIndex` class to implement a singleton pattern by adding
     * a `getInstance` method, ensuring that only one instance of the data index exists regardless of how many times
     * users navigate between pages. This change prevented data reloads and losses, thereby enhancing the efficiency
     * of data handling and improving the overall user experience in the app.
     */


    /**
     * Tohfa proposed and collaborated to implement Singleton Design Pattern in Search.
     * This approach helps maintain consistent state and avoids issues related to
     * multiple instances of the same class being created.
     * Name: ${Tohfa Siddika Barbhuiya}
     * @studentID :- ${u7665856}
     *
     */
    public static synchronized ProductSearchIndex getInstance() {
        if (instance == null) {
            instance = new ProductSearchIndex();
        }
        return instance;
    }

    private void initializeDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("FoodData");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataTree.clear();
                    allFoodData.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FoodData foodData = dataSnapshot.getValue(FoodData.class);
                        if (foodData != null && foodData.getProductName() != null) {
                            dataTree.insert(foodData);
                            allFoodData.add(foodData);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.err.println("Firebase data load cancelled: " + error.getMessage());
                }
            });
        }
    }

    public RedBlackTree getDataTree() {
        return dataTree;
    }
    public List<FoodData> getAllFoodData() {
        return new ArrayList<>(allFoodData);
    }


}
