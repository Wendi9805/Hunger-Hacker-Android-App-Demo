package com.example.groupproject.followFunctions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.groupproject.loadDataFunction.FoodData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * This class is a set of methods that implement the Interact-Follow custom feature to be used in
 * the LoadActivity and FollowingActivity.
 *
 * @author ${Kyle Greenwood}
 */

public class FollowFunctions {

    // Field.
    static String followingListRaw = "";

    // Constructor.
    public FollowFunctions() {
        followingListRaw = "";
    }

    // Return a separated array list of the 'following' string field in the Firestore user data.
    public ArrayList<String> getFollowList(){

        if(Objects.isNull(followingListRaw)){
            return new ArrayList<String>();
        }

        String[] followingArr = followingListRaw.split(",");
        ArrayList<String> followingList = new ArrayList<>();

        for (int i = 0; i < followingArr.length; i++){
            followingList.add(followingArr[i]);
        }

        return followingList;
    }

    /**
     * Verified that data used in this file is persisted in firebase
     * @Verified by:- Muhammad Arslan Amjad Qureshi
     */

    // Get the Firestore document reference for specified user.
    private static DocumentReference getFollowData(String uID){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference userData = database.collection("users").document(uID);
        Log.d("FollowDataMessage","PathSet");

        return userData;
    }

    // Get the 'following' string from the user data in Firestore. Store this in the class object to
    //be accessed later since this is a asynchronous callback function.
    public void getFollowingRaw(String uID){
        final DocumentReference followData = getFollowData(uID);
        followData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot data = task.getResult();
                    Log.d("FollowFunctions getFollowingRaw","Task Successful");

                    // For debugging tell the team that the document reference lead to an empty doc.
                    if(data.exists()) {
                        followingListRaw = (String) data.get("following");
                        if(!Objects.isNull(followingListRaw)){
                            Log.d("Follow Data Retrieved:", followingListRaw);
                        }
                    }else{
                        if(!Objects.isNull(followingListRaw)) {
                            Log.d("Following data DNE", followingListRaw);
                        }
                    }
                }else {
                    Log.d("FollowFunctions FirebaseError:", "Could not retrieve follow data.");
                }
            }
        });
    }

    // Use the existing following string for the logged in user and add a provider to it. Generic
    //implementation allows for extending this to beyond the three large providers if required.
    public static void addFollow(String userToFollow, String uID){
        DocumentReference userData = getFollowData(uID);

        String oldList = "";

        if(!Objects.isNull(followingListRaw)){
            oldList = followingListRaw;
        }


        // Do not attempt to add a provider to follow if it is already followed.
        if(oldList.contains(userToFollow)){
            return;
        }

        // Append new user to following list.
        String newList = oldList + "," + userToFollow;
        if(oldList.isEmpty()){
            newList = userToFollow;
        }


        Map<String, String> followList = new HashMap<>();
        followList.put("following",newList);

        // Update the 'following' field in the logged in user's user data.
        userData.set(followList, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) { Log.d("FollowingFuntions addFollow:", "Success"); }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) { Log.d("FollowingFuntions addFollow:", "Failure"); }
        });
    }

    // Use the seperated array list of the following string of the logged in user and remove a
    //provider to it. Generic implementation allows for extending this to beyond the three large
    //providers if required.
    public static void removeFollow(String userToFollow, String uID, ArrayList<String> oldList){
        DocumentReference userData = getFollowData(uID);

        // Do not attempt to remove a provider if it is already not followed.
        if(!oldList.contains(userToFollow)){
            return;
        }

        oldList.remove(userToFollow);

        Iterator<String> stringMaker = oldList.iterator();
        String data;
        String newList = "";

        // Recreate the string once the desired provider is removed.
        if(stringMaker.hasNext()){
            newList = stringMaker.next();
        }
        while(stringMaker.hasNext()){
            data = stringMaker.next();
            newList = newList + "," + data;
        }

        Map<String, String> followList = new HashMap<>();
        followList.put("following",newList);

        // Update the 'following' field in the logged in user's user data.
        userData.set(followList, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("FollowingFuntions addFollow:", "Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Log.d("FollowingFuntions addFollow:", "Failure");
            }
        });
    }

    public static ArrayList<FoodData> sortFoodData(ArrayList<FoodData> unsortedData, ArrayList<String> followList){

        Iterator<FoodData> sorter = unsortedData.iterator();

        ArrayList<FoodData> toBeRemoved = new ArrayList<>();
        ArrayList<FoodData> sorted = new ArrayList<>();

        FoodData data;

        // Sort the food data using default list iterator and manually checking the elements.
        //Incrementing integer i limits the amount of data being sorted to avoid high load times.
        int i = 0;
        String check = "";
        while(sorter.hasNext() && i < 1000){
            i++;
            data = sorter.next();
            check = data.getProvider();
            if(followList.contains(check)){
                sorted.add(data);
                toBeRemoved.add(data);
            }
        }

        // Prepare data to be shown, with the data from followed providers at the start of the array
        //list and the remaining data added onto the end in a random order.
        unsortedData.removeAll(toBeRemoved);
        Collections.shuffle(unsortedData);
        sorted.addAll(unsortedData);

        return sorted;
    }
}
