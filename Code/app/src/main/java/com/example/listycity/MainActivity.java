package com.example.listycity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declare the variables for referencing the UI elements
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText cityInput;
    Button confirmButton;
    Button addCityButton;
    Button deleteCityButton;

    String selectedCity; // Declare the selectedCity variable

    int lastSelectedView = -1; // For highlighting the last selected item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // creates on create of superclass and sets content view
        super.onCreate(savedInstanceState);
        // sets edge to edge mode (optional)
        EdgeToEdge.enable(this);
        // sets content view to activity_main.xml (not optional)
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the UI elements
        cityList = findViewById(R.id.city_list);
        cityInput = findViewById(R.id.cityInputField);
        confirmButton = findViewById(R.id.confirmButton);
        addCityButton = findViewById(R.id.addCityButton);
        deleteCityButton = findViewById(R.id.deleteCityButton);
        // Initialize the list of cities
        String[] cities = {"Edmonton", "Vancouver", "Moscow"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Hide the input field and confirm button initially
        cityInput.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityInput.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
                cityInput.setText(""); // Clear the input field
            }
        });

        // Confirm button: add to list
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = cityInput.getText().toString().trim();
                if (!newCity.isEmpty() && !dataList.contains(newCity)) {
                    dataList.add(newCity);
                    cityAdapter.notifyDataSetChanged();
                    cityInput.setVisibility(View.GONE);
                    confirmButton.setVisibility(View.GONE);
                }
            }
        });

        // Delete button: remove from list
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCity != null) {
                    // Find the position of the selected city in the list
                    int position = dataList.indexOf(selectedCity);
                    // Remove the selected city from the list
                    dataList.remove(selectedCity);
                    cityAdapter.notifyDataSetChanged();
                    // Reset the background of the last selected item if needed
                    if (lastSelectedView != -1 && lastSelectedView < cityList.getChildCount()) {
                        cityList.getChildAt(lastSelectedView).setBackgroundColor(Color.TRANSPARENT);
                    }
                    // Reset selection
                    selectedCity = null;
                    lastSelectedView = -1; // No city selected anymore
                } else {
                    // If no city is selected, show a toast message to inform the user
                    // Adapted from https://developer.android.com/guide/topics/ui/controls/toasts and in built gemini suggestions
                    Toast.makeText(MainActivity.this, "Please select a city to delete.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // List View click listener
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            if (lastSelectedView != -1) {
                // Clear the background color of the previously selected item
                cityList.getChildAt(lastSelectedView).setBackgroundColor(Color.TRANSPARENT);
            }
            // Highlight the selected item to light gray
            lastSelectedView = position;
            selectedCity = dataList.get(position);
            view.setBackgroundColor(Color.LTGRAY);
        });

        }

    }