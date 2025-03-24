package com.example.groupproject.fbPersistAndExtension;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Utility class to handle Firebase initialization and connection monitoring.
 * @author Muhammad Arslan Amjad Qureshi
 */
public class FirebaseUtils {

    /**
     * Initializes Firebase with persistence capabilities and sets up a connection status listener.
     */
    public static void initializeFirebase() {
        // Enable Firebase's offline capabilities
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Add a listener to monitor connection changes
        FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve and print connection status
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("Connected to Firebase");
                } else {
                    System.out.println("Disconnected from Firebase");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Print error message if the listener is cancelled
                System.err.println("Listener was cancelled, error: " + databaseError.getMessage());
            }
        });
    }
}
