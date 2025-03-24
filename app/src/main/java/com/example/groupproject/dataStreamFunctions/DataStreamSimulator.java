package com.example.groupproject.dataStreamFunctions;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a real-time data stream from Firebase, notifying registered observers of new data.
 * Utilizes the Observer pattern to allow components to react to data changes without tight coupling.
 * This class extends AppCompatActivity to potentially leverage lifecycle methods, although typically,
 * data management classes should not extend activity classes.
 *
 * @author ${Muhammad Arslan Amjad Qureshi}
 * @studentId ${u7632488}
 */
public class DataStreamSimulator extends AppCompatActivity {

    private final Context context; // Application context to be used within the class
    private final Handler handler = new Handler(Looper.getMainLooper()); // Handler to manage threading
    private final DatabaseReference databaseReference; // Reference to the Firebase database path
    private final List<UserActionObservers> observers = new ArrayList<>(); // List of observers to notify

    /**
     * Constructs a new DataStreamSimulator.
     *
     * @param context The application context, used for initializing database references.
     */
    public DataStreamSimulator(Context context) {
        this.context = context;
        // Initialize Firebase Reference to "actionInteractions"

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         * @author:- Muhammad Arslan Amjad Qureshi
         * @studentID :- u7632488
         */

        this.databaseReference = FirebaseDatabase.getInstance().getReference("actionInteractions");
        listenForDataChanges(); // Start listening for data changes immediately
    }

    /**
     * Sets up a ValueEventListener to listen for real-time updates from Firebase.
     * Notifies all registered observers whenever data changes.
     */
    private void listenForDataChanges() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserActions action = snapshot.getValue(UserActions.class);
                    notifyObservers(action); // Notify all observers about the new action
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DataStreamSimulator", "Error loading data from Firebase", databaseError.toException());
            }
        });
    }

    /**
     * Adds an observer to the list if it is not already included.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(UserActionObservers observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer from the list.
     *
     * @param observer The observer to be removed.
     */
    public void removeObserver(UserActionObservers observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers with the latest action.
     *
     * @param action The action to be disseminated to observers.
     */
    private void notifyObservers(UserActions action) {
        for (UserActionObservers observer : observers) {
            observer.onUserActionReceived(action); // Each observer handles the action
        }
    }

    /**
     * Starts listening for data updates. This method reactivates the listening mechanism
     * if it was previously halted or if the app requires a re-initialization of the data stream.
     */
    public void startDataStreamSimulator() {
        listenForDataChanges(); // Re-initiate listening to Firebase updates
    }

    /**
     * Stops the simulation of the data stream by removing all event listeners.
     * This should be called to clean up resources when the app is closed or when
     * the data stream is no longer needed.
     * Used on the onDestroy() method in the Load Activity.Java but still didn't
     * used because our app doesn't supports the sign out or destroying the load activity.
     */
    public void stopDataStreamSimulator() {
        databaseReference.removeEventListener((ValueEventListener) this);
    }

    /**
     * @author ${Muhammad Arslan Amjad Qureshi}
     * @studentId ${u7632488}
     * Detected Code Smell on code committed before 1st May, 2024
     * Refactored it when making the code final.
     */

    /**
     * CODE SMELL EXPLANATION IN DETAIL
     *
     * TYPE OF CODE SMELL THAT WAS DETECTED:- “Inappropriate Intimacy”
     *
     * What it is?
     * “Inappropriate Intimacy” is a code smell that occurs when classes
     *  or modules are too tightly coupled, leading to excessive interactions
     *  or dependencies.
     *
     *
     *  DETAILED EXPLANATION:-
     * Initially, our project and specifically DataStreamPart used an inappropriate intimacy code
     * smell by storing and retrieving
     * data directly from a local JSON file within the assets folder, which tightly coupled the
     * application's functionality with its data storage mechanism and limited scalability.
     * This setup also exhibited speculative generality, as it was an over-engineered solution
     * for the project's requirements, which specifically mandated using Firebase for data handling.
     * Recognizing these issues, I refactored the implementation by moving the data storage
     * to Firebase(Real Time Database), thus decoupling the data management from the local system and enhancing
     * the application's flexibility and scalability. This transition not only aligned the
     * project with its specified requirements—ensuring compliance and supporting dynamic
     * data changes and real-time updates—but also improved maintainability and extensibility
     * by leveraging Firebase's capabilities to handle data synchronization, backup, and security
     * automatically, which are crucial for applications at scale and in multi-user environments.
     */
}
