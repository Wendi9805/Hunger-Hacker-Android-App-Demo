package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.followFunctions.FollowFunctions;
import com.example.groupproject.dataGraphicalViewer.DataGraphicalFunction;
import com.example.groupproject.dataProfileFunction.ProfileActivity;
import com.example.groupproject.dataStreamFunctions.DataStreamSimulator;
import com.example.groupproject.dataStreamFunctions.UserActionObservers;
import com.example.groupproject.dataStreamFunctions.UserActions;
import com.example.groupproject.databinding.ActivityLoadBinding;
import com.example.groupproject.databinding.ActivityUserListBinding;
import com.example.groupproject.loadDataFunction.FoodData;
import com.example.groupproject.p2pFunction.Constants;
import com.example.groupproject.p2pFunction.PreferenceManager;
import com.example.groupproject.p2pFunction.DataAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;

/**
 * This page is load data from database and display them for users to read
 * there are over 2500 food data instances in Firebase database, so each time, we choose 15 random instances to load and display
 * there's a button on the top left, user can use it to refresh the page and get 15 new random foodData instances
 * there's a button on the top left, user can use it post their food into and add it in the database
 *
 * @author ${Wendi Fan}
 */

public class LoadActivity extends AppCompatActivity implements UserActionObservers {

    private DataStreamSimulator dataStreamSimulator;
    RecyclerView recyclerView;
    DatabaseReference database;
    DataAdapter dataAdapter;
    ArrayList<FoodData> list;

    // Kyle added code here for follow feature.
    FollowFunctions func;
    // Code end

    FirebaseAuth auth;

    private ActivityLoadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         * @studentID :- u7632488
         */

        // Kyle added code here for follow feature.
        func = new FollowFunctions();
        auth = FirebaseAuth.getInstance();
        String uID = auth.getCurrentUser().getUid();
        func.getFollowingRaw(uID);
        // Code ends

        //get foodData from Firebase
        recyclerView = findViewById(R.id.loadData); //initialize the recyclerView
        database = FirebaseDatabase.getInstance().getReference("FoodData"); //retrieve data from firebase

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //use adapter to display data in recyclerView
        list = new ArrayList<>();
        dataAdapter = new DataAdapter(this, list);
        recyclerView.setAdapter(dataAdapter);

        //buttons to jump from one page to another
        Button searchButton = findViewById(R.id.searchButton);
        Button loadButton = findViewById(R.id.homeButton);
        Button userListButton = findViewById(R.id.userListButton);
        Button profileButton = findViewById(R.id.profile);
        Button dataButton = findViewById(R.id.data);
        AppCompatImageView refreshImageView = findViewById(R.id.refresh);
        AppCompatImageView postImageView = findViewById(R.id.post);

        //refresh the page and get new food data -- Used for follow feature to data from providers
        //user follows instead of random data.
        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(true);
                Toast.makeText(getApplicationContext(), "Refresh and get some new posts", Toast.LENGTH_LONG).show();
            }
        });

        //go to the posting page
        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });


        loadData(false); // Initial load -- False means random data loaded.

        loadButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoadActivity.this, LoadActivity.class);
            startActivity(intent);
        });

        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoadActivity.this, SearchPage.class);
            startActivity(intent);
        });

        userListButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoadActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoadActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        dataButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoadActivity.this, DataGraphicalFunction.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Tohfa collaborated to help adding and refactoring the data stream part from MainActivity.java to LoadActivity.java
        // Initialize the DataStreamSimulator with the application context
        dataStreamSimulator = new DataStreamSimulator(this);
        dataStreamSimulator.addObserver(this);  // Register this activity as an observer

        // Start the data stream simulator
        dataStreamSimulator.startDataStreamSimulator();
    }

    /**
     * @author ${Tohfa Siddika Barbhuiya} of Code smell
     * @studentId ${u7665856}
     * Detected Code Smell on code committed before 1st May, 2024
     * Refactored it by helping my teammate when making the code final.
     */

    /**
     * CODE SMELL EXPLANATION IN DETAIL
     *
     * TYPE OF CODE SMELL THAT WAS DETECTED:- “Lazy Class Code Smell”
     *
     * What it is?
     * Lazy class code smell refers to a class that doesn't justify
     * its existence due to lack of meaningful functionality or
     * contribution to the system's behavior, often resulting in
     * unnecessary complexity and maintenance overhead.

     *  DETAILED EXPLANATION:-
     * In the initial phase of our project, the data stream simulator was implemented
     * in the Main Activity.java. However, following our project's evolution and
     * restructuring on 1st May in preparation for checkpoint 2, a new LoadActivity.java
     * was created to handle various functionalities, including the intended integration of
     * the data stream simulator. Unfortunately, the datastream simulator was not moved and
     * remained in Main Activity.java, which was no longer serving a significant role in our
     * application. This oversight led to a 'Lazy Class' code smell as Main Activity.java
     * became underutilized. I recognized this inefficiency and I coordinated with the
     * responsible team member to reallocate and refactor the data stream simulator to
     * LoadActivity.java, aligning it with the revised architectural design of our application.
     */
    //load data from firebase and put data in the list
    private void loadData(boolean sortFollow) {

        //Everytime, only 350 data would display
        int maxItems = 350;
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<FoodData> tempList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodData foodData = dataSnapshot.getValue(FoodData.class);
                    tempList.add(foodData);
                }

                // Kyle added code here for follow feature.
                if(!sortFollow){
                    // If sort follow set false randomise load order.
                    Collections.shuffle(tempList);
                }
                if(sortFollow){
                    // If sort follow set true filter data so data from followed
                    //providers is loaded first.
                    tempList = func.sortFoodData(tempList, func.getFollowList());
                }
                // Code ends

                list.clear();

                for (int i = 0; i < Math.min(maxItems, tempList.size()); i++) {
                    list.add(tempList.get(i));
                }

                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });
    }

    /**
     * @author ${Muhammad Arslan Amjad Qureshi}
     * @studentId ${u7632488}
     *
     * Wrote the method onUserActionReceived
     */
    @Override
    public void onUserActionReceived(UserActions action) {
        // Handle the user action received from the data stream
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoadActivity.this,  action.getUserId() + ": " +action.getContent(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @author ${Muhammad Arslan Amjad Qureshi}
     * @studentId ${u7632488}
     *
     * Wrote the method onDestroy()
     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (dataStreamSimulator != null) {
//            dataStreamSimulator.removeObserver(this);  // Unregister the observer
//            dataStreamSimulator.stopDataStreamSimulator();  // Stop the data stream simulator
//        }
//    }
}