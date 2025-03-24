package com.example.groupproject.dataGraphicalViewer;

import com.example.groupproject.SearchPage;
import com.example.groupproject.LoadActivity;
import com.example.groupproject.UserListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.groupproject.R;
import com.example.groupproject.dataProfileFunction.ProfileActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *This DataGraphicalFunction class fetches offer data from Firebase based on a selected state,
 * counts the number of offers of each type, and visualizes the data using a bar chart.
 * It demonstrates usage of Firebase Realtime Database,dynamic chart creation,
 * and integration with Android UI elements. Users can refer to it to get details about food
 * availabilty in their state and can do better search after that to grab the foods.
 * authored and implemented the complete feature requirement end to end.
 *
 * @author ${Tohfa Siddika Barbhuiya}
 * @studentID :- ${u7665856}
 *
 * The same author also made the datafiles basic feature with more than 2500 instances persisting in realtime database of firestore.
 * So, mentioning the same here as well.
 */

public class DataGraphicalFunction extends AppCompatActivity {
    // Firebase Database reference
    private DatabaseReference foodDatabase;
    // BarChart instance
    private BarChart barChart;
    // Spinner for selecting state
    private Spinner stateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_graphical_function);

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         * @studentID :- u7632488
         */


        // Initialize Firebase Database reference
        foodDatabase = FirebaseDatabase.getInstance().getReference().child("FoodData");
        // Initialize BarChart
        barChart = findViewById(R.id.bar_chart);
        // Initialize Spinner
        stateSpinner = findViewById(R.id.state_spinner);

        // Set up the Spinner with state names
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        // Set up a listener for the Spinner
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected state
                String selectedState = parent.getItemAtPosition(position).toString();
                // Fetch data and draw the chart based on the selected state
                fetchDataAndDrawChart(selectedState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DataGraphicalFunction.this, "Please select a state", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonSearchButton = findViewById(R.id.searchButton);
        Button loadButton = findViewById(R.id.homeButton);
        Button userListButton = findViewById(R.id.userListButton);
        Button profileButton = findViewById(R.id.profile);
        Button dataButton = findViewById(R.id.data);

        //UI clicks
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataGraphicalFunction.this, LoadActivity.class);
                startActivity(intent);
            }
        });
        buttonSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataGraphicalFunction.this, SearchPage.class);
                startActivity(intent);
            }
        });
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataGraphicalFunction.this, UserListActivity.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataGraphicalFunction.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataGraphicalFunction.this, DataGraphicalFunction.class);
                startActivity(intent);
            }
        });
    }
    //UI clicks ends

    // Method to fetch data from Firebase Database and draw the chart
    private void fetchDataAndDrawChart(final String state) {
        foodDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // HashMap to store offer counts
                HashMap<String, Integer> offerCounts = new HashMap<>();

                // Iterate through the dataSnapshot to count offers
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String offerType = snapshot.child("offer").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    if (address != null && address.contains(state)) {
                        if (offerCounts.containsKey(offerType)) {
                            offerCounts.put(offerType, offerCounts.get(offerType) + 1);
                        } else {
                            offerCounts.put(offerType, 1);
                        }
                    }
                }
                // Draw the bar chart using the obtained offer counts
                drawBarChart(offerCounts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(DataGraphicalFunction.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to draw the bar chart
    private void drawBarChart(HashMap<String, Integer> offerCounts) {
        // ArrayLists to store bar entries and labels
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        // Iterate through the offerCounts and add entries and labels
        int index = 0;
        for (Map.Entry<String, Integer> entry : offerCounts.entrySet()) {
            String offerType = entry.getKey();
            Integer count = entry.getValue();
            barEntries.add(new BarEntry(index, count));
            labels.add(offerType);
            index++;
        }

        // Create a BarDataSet with the bar entries and set color
        BarDataSet dataSet = new BarDataSet(barEntries, "Offer Types");
        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        dataSet.setColor(color); // Set color

        // Create BarData and set it to the BarChart
        BarData barData = new BarData(dataSet);

        // Set XAxis properties
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        // Set the data to the BarChart and invalidate it
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
}

