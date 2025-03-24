package com.example.groupproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupproject.dataGraphicalViewer.DataGraphicalFunction;
import com.example.groupproject.dataProfileFunction.ProfileActivity;
import com.example.groupproject.databinding.ActivityUserListBinding;
import com.example.groupproject.p2pFunction.PreferenceManager;
import com.example.groupproject.p2pFunction.User;
import com.example.groupproject.p2pFunction.Constants;
import com.example.groupproject.p2pFunction.UserListener;
import com.example.groupproject.p2pFunction.UsersAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This page is userList page, user can see every friends they have
 * userLists will be loaded automatically
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class UserListActivity extends AppCompatActivity implements UserListener {

    private ActivityUserListBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();

        //buttons to jump from one page to another
        Button buttonSearchButton = findViewById(R.id.searchButton);
        Button loadButton = findViewById(R.id.homeButton);
        Button userListButton = findViewById(R.id.userListButton);
        Button profileButton = findViewById(R.id.profileB);
        Button dataButton = findViewById(R.id.data);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, LoadActivity.class);
                startActivity(intent);
            }

        });
        buttonSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, SearchPage.class);
                startActivity(intent);
            }

        });
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, UserListActivity.class);
                startActivity(intent);
            }

        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, ProfileActivity.class);
                startActivity(intent);
            }

        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, DataGraphicalFunction.class);
                startActivity(intent);
            }

        });
    }

    //When user click backButton, we can go back
    private void setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS).
                get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserID = preferenceManager.getString(Constants.KEY_USER_ID);
                    if(task.isSuccessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                            if(currentUserID.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_USER_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if(users.size() > 0){
                            UsersAdapter usersAdapter = new UsersAdapter(users,this);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        }else {
                            showErrorMessage();
                        }
                    }else {
                        showErrorMessage();
                    }
                });
    }

//    //Change Firestore into realtime database
//    private void getUsers() {
//        loading(true);
//
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.KEY_COLLECTION_USERS);
//
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                loading(false);
//                // Replaced with the actual user ID obtained from the PreferencesManager
//                String currentUserID = "4hvhlkYwtve8t84u7klE";
//                List<User> users = new ArrayList<>();
//
//                // Iterate through all child nodes and create user objects
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    // Skip if current node ID matches current user ID
//                    if (currentUserID.equals(snapshot.getKey())) {
//                        continue;
//                    }
//
//                    // Create User object and set up data
//                    User user = new User();
//                    user.name = snapshot.child(Constants.KEY_USER_NAME).getValue(String.class);
//                    user.email = snapshot.child(Constants.KEY_EMAIL).getValue(String.class);
//                    user.image = snapshot.child(Constants.KEY_IMAGE).getValue(String.class);
//                    user.id = snapshot.getKey(); // Use the node's key as the user ID
//                    users.add(user);
//                }
//
//                // If the user list is obtained, it is displayed in RecyclerView
//                if (users.size() > 0) {
//                    UsersAdapter usersAdapter = new UsersAdapter(users, UserListActivity.this);
//                    binding.usersRecyclerView.setAdapter(usersAdapter);
//                    binding.usersRecyclerView.setVisibility(View.VISIBLE);
//                } else {
//                    showErrorMessage();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                loading(false);
//                showErrorMessage();
//            }
//        });
//    }

    //If there is no UserList data, it will show an error here
    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s","No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    //When it is loading userList data, it will show a progress bar here
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //When use clicks any user, will jump to chat page
    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra(Constants.KEY_USER,user); //Pass User as additional data to the target activity
        startActivity(intent); //Use the startActivity method to start a new ChatActivity and pass the Intent containing user information
        finish(); //Close the current activity, UserListActivity
    }
}

/**
 * Verified that data used in this file is persisted in firebase
 * @Verified by:- Muhammad Arslan Amjad Qureshi
 * @studentID :- u7632488
 */