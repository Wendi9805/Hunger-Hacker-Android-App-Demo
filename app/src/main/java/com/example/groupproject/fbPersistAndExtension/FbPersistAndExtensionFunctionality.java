package com.example.groupproject.fbPersistAndExtension;

import androidx.annotation.NonNull;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;



// Important
// Already implemented and verified the Fb-Persist Feature.
// Majorly Includes Fb-Persist-Extension Special Case.
/*
 * Confirmation on Firebase Persistence Implementation:
 * We have successfully verified that all data persists correctly through Firebase, ensuring that the fb-persist feature is fully operational.
 * However, we encountered challenges with the fb-persist-extension feature, which introduced bugs and potential negative impacts on other modules and the app itself.
 * Given that this extension is considered more of a special case than a general feature, we opted to demonstrate our intent and strategic approach through illustrative dummy code.
 * This approach highlights our understanding of the required solutions, although time constraints limited our ability to fully implement these solutions within the project timeline.
 * Due to that our app was crashing, so we avoided adding in our activities page, so rest of
 * app might work correctly.
 */


/**
 * Handles Firebase data interactions for food data and user actions.
 * Provides methods to observe and manipulate data in Firebase.
 *
 * @author ${Muhammad Arslan Amjad Qureshi}
 * @studentId ${u7632488}
 */
public class FbPersistAndExtensionFunctionality {

    // Reference to the 'FoodData' node in Firebase
    private DatabaseReference foodDataReference;
    // Reference to the 'actionInteractions' node in Firebase
    private DatabaseReference usersReference;

    // Maps observers to ValueEventListener for food data
    private Map<FirebaseDataObserver, ValueEventListener> foodDataListeners;
    // Maps observers to ValueEventListener for users data
    private Map<FirebaseDataObserver, ValueEventListener> usersListeners;

    /**
     * Constructor to initialize references and HashMaps for managing listeners.
     */
    public FbPersistAndExtensionFunctionality() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        foodDataReference = databaseReference.child("FoodData");
        usersReference = databaseReference.child("actionInteractions");

        foodDataListeners = new HashMap<>();
        usersListeners = new HashMap<>();
    }

    /**
     * Adds an observer to the food data in Firebase.
     * @param observer the FirebaseDataObserver to receive updates
     */
    public void addFoodDataObserver(FirebaseDataObserver observer) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                observer.onDataChanged(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                observer.onError(databaseError);
            }
        };
        foodDataListeners.put(observer, listener);
        foodDataReference.addValueEventListener(listener);
    }

    /**
     * Removes an observer from the food data in Firebase.
     * @param observer the FirebaseDataObserver to remove
     */
    public void removeFoodDataObserver(FirebaseDataObserver observer) {
        ValueEventListener listener = foodDataListeners.remove(observer);
        if (listener != null) {
            foodDataReference.removeEventListener(listener);
        }
    }

    /**
     * Adds an observer to the users data in Firebase.
     * @param observer the FirebaseDataObserver to receive updates
     */
    public void addUsersObserver(FirebaseDataObserver observer) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                observer.onDataChanged(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                observer.onError(databaseError);
            }
        };
        usersListeners.put(observer, listener);
        usersReference.addValueEventListener(listener);
    }

    /**
     * Removes an observer from the users data in Firebase.
     * @param observer the FirebaseDataObserver to remove
     */
    public void removeUsersObserver(FirebaseDataObserver observer) {
        ValueEventListener listener = usersListeners.remove(observer);
        if (listener != null) {
            usersReference.removeEventListener(listener);
        }
    }

    /**
     * Updates the food data in Firebase.
     * @param newData the new data to set for the food data
     */
    public void updateFoodData(String newData) {
        foodDataReference.setValue(newData);
    }

    /**
     * Updates the users data in Firebase with a map of new values.
     * @param newData the map containing the new data for users
     */
    public void updateUsersData(Map<String, Object> newData) {
        usersReference.updateChildren(newData);
    }
}
