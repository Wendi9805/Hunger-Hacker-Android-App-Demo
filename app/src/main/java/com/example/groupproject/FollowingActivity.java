package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupproject.dataGraphicalViewer.DataGraphicalFunction;
import com.example.groupproject.dataProfileFunction.ProfileActivity;
import com.example.groupproject.followFunctions.FollowFunctions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * This activity has been implimented as the intended feature of being able to view other user's
 * profiles and subsequently follow them there (including providers) was not implimented due to time
 * constraints. The choice to not implimenet this was due largely to it not being a custom feature
 * option in the assignment specification. This activity lets the user follow/unfollow the three
 * major providers in Australia.
 *
 * @author ${Kyle Greenwood}
 * @studentId ${u7472943}
 */

public class FollowingActivity extends AppCompatActivity {

    FollowFunctions func;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         * @studentID :- u7632488
         */

        // Get user ID.
        auth = FirebaseAuth.getInstance();
        String uID = auth.getCurrentUser().getUid();

        // Preload the list of providers the user follows.
        func = new FollowFunctions();
        func.getFollowingRaw(uID);

        Button followWoolies = findViewById(R.id.btn_follow_woolies);
        Button unfollowWoolies = findViewById(R.id.btn_unfollow_woolies);
        Button followColes = findViewById(R.id.btn_follow_coles);
        Button unfollowColes = findViewById(R.id.btn_unfollow_coles);
        Button followAldi = findViewById(R.id.btn_follow_aldi);
        Button unfollowAldi = findViewById(R.id.btn_unfollow_aldi);

        Button buttonSearchButton = findViewById(R.id.searchButton);
        Button loadButton = findViewById(R.id.homeButton);
        Button userListButton = findViewById(R.id.userListButton);
        Button profileButton = findViewById(R.id.profile);
        Button dataButton = findViewById(R.id.data);

        // Buttons call relevant FollowFunctions methods and pass them required data.
        followWoolies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func.getFollowingRaw(uID);
                FollowFunctions.addFollow("Woolworths",uID);
                Toast.makeText(getApplicationContext(), "Woolworths Followed", Toast.LENGTH_LONG).show();
            }
        });
        unfollowWoolies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func.getFollowingRaw(uID);
                ArrayList<String> oldList = func.getFollowList();
                FollowFunctions.removeFollow("Woolworths",uID,oldList);
                Toast.makeText(getApplicationContext(), "Woolworths Unfollowed", Toast.LENGTH_LONG).show();
            }
        });

        followColes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func.getFollowingRaw(uID);
                FollowFunctions.addFollow("Coles",uID);
                Toast.makeText(getApplicationContext(), "Coles Followed", Toast.LENGTH_LONG).show();
            }
        });
        unfollowColes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func.getFollowingRaw(uID);
                ArrayList<String> oldList = func.getFollowList();
                FollowFunctions.removeFollow("Coles",uID,oldList);
                Toast.makeText(getApplicationContext(), "Coles Unfollowed", Toast.LENGTH_LONG).show();
            }
        });

        followAldi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func.getFollowingRaw(uID);
                FollowFunctions.addFollow("Aldi",uID);
                Toast.makeText(getApplicationContext(), "Aldi Followed", Toast.LENGTH_LONG).show();
            }
        });
        unfollowAldi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func.getFollowingRaw(uID);
                ArrayList<String> oldList = func.getFollowList();
                FollowFunctions.removeFollow("Aldi",uID,oldList);
                Toast.makeText(getApplicationContext(), "Aldi Unfollowed", Toast.LENGTH_LONG).show();
            }
        });

        // Application navigation -- Not coded by me.
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
                startActivity(intent);
                finish();
            }

        });
        buttonSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                startActivity(intent);
                finish();
            }

        });
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                startActivity(intent);
                finish();
            }

        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }

        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DataGraphicalFunction.class);
                startActivity(intent);
                finish();
            }

        });
    }
}