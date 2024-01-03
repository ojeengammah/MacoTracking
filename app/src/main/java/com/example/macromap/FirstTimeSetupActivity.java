package com.example.macromap;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FirstTimeSetupActivity extends AppCompatActivity {
    private EditText editTextName; // Define similar for Age, Gender, etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firsttime_setup);

        editTextName = findViewById(R.id.editTextName);
        // Initialize other fields...

        Button skipButton = findViewById(R.id.buttonSkip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstTimeSetupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // Find the button by its ID
        final Button weightButton = findViewById(R.id.buttonWeight);
        // Set an OnClickListener to change the button text when pressed
        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the current text and change it accordingly
                if ("lbs".equals(weightButton.getText().toString())) {
                    weightButton.setText("kg");
                } else {
                    weightButton.setText("lbs");
                }
            }
        });


        // Find the button by its ID
        final Button heightButton = findViewById(R.id.buttonHeight);
        // Set an OnClickListener to change the button text when pressed
        heightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the current text and change it accordingly
                if ("ft in".equals(heightButton.getText().toString())) {
                    heightButton.setText("cm");
                } else {
                    heightButton.setText("ft in");
                }
            }
        });



        // Get reference to the Spinner
        Spinner spinner = findViewById(R.id.dropdownSpinnerGender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void saveData(View view) {
        String name = editTextName.getText().toString();
        // Fetch data for other fields...

        // Save this data locally using SharedPreferences or to a database.
    }
}
