package com.example.groupproject.fbPersistAndExtension;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Interface for observing changes to data in Firebase.
 * Implementations of this interface can be registered to receive updates
 * about data changes or errors in Firebase database operations.
 *
 * @author Muhammad Arslan Amjad Qureshi
 * @studentId u7632488
 */
public interface FirebaseDataObserver {

    /**
     * Called when there is a change in the data at the observed location.
     * @param dataSnapshot the current data at the location, encapsulates the data and its metadata
     */
    void onDataChanged(DataSnapshot dataSnapshot);

    /**
     * Called when an error occurs while attempting to access the database.
     * Implementations can use this method to handle database errors gracefully.
     * @param databaseError an object containing details about the error that occurred
     */
    void onError(DatabaseError databaseError);

}
