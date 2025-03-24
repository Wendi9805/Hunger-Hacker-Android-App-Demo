package com.example.groupproject.dataStreamFunctions;

import androidx.annotation.NonNull;

/**
 * @author ${Muhammad Arslan Amjad Qureshi}
 * @studentId ${u7632488}
 * <p>
 * Represents a user action within the application, capturing details such as the user ID,
 * type of action, content of the action, and the timestamp when the action occurred.
 * This class is designed to facilitate serialization and deserialization with Firebase Realtime Database.
 *
 */
public class UserActions {

    // User ID associated with the action
    private String userID;
    // Type of action, e.g., "announcement", "question", "response"
    private String TypeAction;
    // Detailed content of the action
    private String content;
    // Timestamp indicating when the action occurred
    private long timeMarker;

    /**
     * Default constructor required for Firebase deserialization.
     */
    public UserActions() {
        // Default constructor required for calls to DataSnapshot.getValue(UserActions.class)
    }

    /**
     * Constructs a new UserActions instance with detailed information about the user action.
     *
     * @param userID The unique identifier for the user.
     * @param TypeAction The type of action performed by the user.
     * @param content The descriptive content associated with the action.
     * @param timeMarker The timestamp marking the time of the action.
     */
    public UserActions(String userID, String TypeAction, String content, long timeMarker) {
        this.userID = userID;
        this.TypeAction = TypeAction;
        this.content = content;
        this.timeMarker = timeMarker;
    }

    /**
     * Gets the user ID.
     *
     * @return The user ID.
     */
    public String getUserId() {
        return userID;
    }

    /**
     * Sets the user ID.
     *
     * @param userID The user ID to set.
     */
    public void setUserId(String userID) {
        this.userID = userID;
    }

    /**
     * Gets the type of action.
     *
     * @return The type of action.
     */
    public String getTypeAction() {
        return TypeAction;
    }

    /**
     * Sets the type of action.
     *
     * @param TypeAction The type of action to set.
     */
    public void setTypeAction(String TypeAction) {
        this.TypeAction = TypeAction;
    }

    /**
     * Gets the content of the action.
     *
     * @return The content of the action.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the action.
     *
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the timestamp of the action.
     *
     * @return The timestamp of the action.
     */
    public long getTimeMarker() {
        return timeMarker;
    }

    /**
     * Sets the timestamp of the action.
     *
     * @param timeMarker The timestamp to set.
     */
    public void setTimeMarker(long timeMarker) {
        this.timeMarker = timeMarker;
    }

    /**
     * Provides a string representation of the user action, formatted as a CSV line for convenience.
     *
     * @return A string representation of the user action.
     */
    @NonNull
    @Override
    public String toString() {
        return userID + ',' + TypeAction + "," + content + "," + timeMarker;
    }
}
