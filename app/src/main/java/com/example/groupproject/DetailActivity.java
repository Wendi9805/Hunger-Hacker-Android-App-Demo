package com.example.groupproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.groupproject.R;
import com.example.groupproject.loadDataFunction.FoodData;

/**
 * This page is to display details of one foodData instance
 * users can see every attributes of this foodData
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get food data from previous activity
        FoodData foodData = (FoodData) getIntent().getSerializableExtra("foodData");

        TextView productName = findViewById(R.id.attribute11);
        TextView category = findViewById(R.id.attribute22);
        TextView date = findViewById(R.id.attribute33);
        TextView expiration = findViewById(R.id.attribute44);
        TextView provider = findViewById(R.id.attribute55);
        TextView address = findViewById(R.id.attribute66);
        TextView offer = findViewById(R.id.attribute77);
        TextView eventName = findViewById(R.id.attribute88);
        TextView phoneNumber = findViewById(R.id.attribute99);

        //set the values of attributes and display them
        productName.setText(foodData.getProductName());
        date.setText(foodData.getDate());
        expiration.setText(foodData.getExpiration());
        provider.setText(foodData.getProvider());
        address.setText(foodData.getAddress());
        offer.setText(foodData.getOffer());
        eventName.setText(foodData.getEventName());
        phoneNumber.setText(foodData.getPhoneNumber());
        category.setText(foodData.getCategory());

    }
}