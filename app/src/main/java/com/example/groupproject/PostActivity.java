package com.example.groupproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupproject.loadDataFunction.FoodData;
import com.example.groupproject.p2pFunction.Constants;
import com.example.groupproject.p2pFunction.PreferenceManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This page is to let user post foodData and add it in Firebase
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class PostActivity extends AppCompatActivity {
    private EditText productName, category, date, expiration, address, offer, eventName, phoneNumber, state;
    private DatabaseReference databaseReference;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        preferenceManager = new PreferenceManager(getApplicationContext());

        productName = findViewById(R.id.postProductName);
        category = findViewById(R.id.postCategory);
        date = findViewById(R.id.postDate);
        expiration = findViewById(R.id.postExpiration);
        address = findViewById(R.id.postAddress);
        offer = findViewById(R.id.postOffer);
        eventName = findViewById(R.id.postEventName);
        phoneNumber = findViewById(R.id.postPhoneNumber);
        state= findViewById(R.id.postState);

        Button postButton = findViewById(R.id.postButton);

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         * @studentID :- u7632488
         */

        databaseReference = FirebaseDatabase.getInstance().getReference("FoodData");

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFoodData();
            }
        });
    }

    private void saveFoodData() {
        String pName = productName.getText().toString().trim();
        String cat = category.getText().toString().trim();
        String dat = date.getText().toString().trim();
        String exp = expiration.getText().toString().trim();
        String addr = address.getText().toString().trim();
        String off = offer.getText().toString().trim();
        String event = eventName.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String stateText = state.getText().toString().trim();

        //set values of every attributes of this food data instance
        FoodData foodData = new FoodData();
        foodData.productName = pName;
        foodData.category = cat;
        foodData.date = dat;
        foodData.expiration = exp;
        foodData.provider = preferenceManager.getString(Constants.KEY_USER_NAME);
        foodData.address = addr;
        foodData.offer = off;
        foodData.eventName = event;
        foodData.phoneNumber = phone;
        foodData.state = stateText;

        databaseReference.push().setValue(foodData);
        Toast.makeText(getApplicationContext(), "You have shared a new post", Toast.LENGTH_LONG).show();
    }
}