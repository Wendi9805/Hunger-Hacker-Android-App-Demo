package com.example.groupproject.dataStreamFunctions;

/**
 * Defines an interface for observing user actions.
 * Classes implementing this interface can react to user actions by implementing the
 * onUserActionReceived method, which will be called whenever a new UserActions instance
 * is broadcast by the DataStreamSimulator.
 *
 * @author ${Muhammad Arslan Amjad Qureshi}
 * @studentId ${u7632488}
 */
public interface UserActionObservers {

    /**
     * This method is called when a new UserActions instance is received.
     * Implementers should define the behavior for handling the received user action.
     *
     * @param action The UserActions instance containing details about the user's action.
     */
    void onUserActionReceived(UserActions action);
}