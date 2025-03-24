package com.example.groupproject.p2pFunction;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * The main function of this class is to store data that can be used all the time when our app runs,
 * such as the userID, image, and name of current user
 * Using the Facade pattern to organize complex inner functions
 *
 * @author ${Wendi Fan}
 */

// Code Smell: This class created to help solve code smell for our program design (committed before April 30th)
// At the beginning of our code development, we didn't realize that the information of the currently active user was integral throughout the entire process.
// This awareness only came later when I was developing the peer-to-peer (p2p) functionality. For instance, if our current user posts a message, we need details like the user's name and image.
// Similarly, when sending messages to friends, we need to check the current user's ID to determine who is the sender and who is the receiver, as their chat logs and views are displayed differently.
// However, creating a new user instance to fetch this information each time was a cumbersome and error-prone process.
// After some reflection, I decided to create a separate class called PreferenceManager. This object can store the current user's information throughout the app's runtime and continuously pass it to the activities that require this information.
// This setup makes it easy to access the current user's details whenever needed.
//
// After identifying this area for improvement, I created the PreferenceManager class, and added and modified some code
// in the LoginActivity and RegistrationActivity (committed before April 30th) to fix the code smell about our app design.
// This ensures that the current user is saved each time they log in or register, facilitating easy access to various user details in subsequent uses.
// Additionally, I used the Façade pattern to design this class, which simplifies its usage and enhances the stability of the code.

public class PreferenceManager {
    private final SharedPreferences sharedPreferences;

    //the constructor for this class
    public PreferenceManager(Context context){
        //MODE_PRIVATE means only the one that creates this preference can get access to it
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME,Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, Boolean value){
        //use Editor to change the data in SharedPreference
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        //apply your change, apply method is asynchronous but commit method is synchronous
        editor.apply();
    }

    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }


    //Clear all the saved data, use it when user want to delete their accounts
    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
