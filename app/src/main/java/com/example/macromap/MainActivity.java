package com.example.macromap;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Macro Values & Goals
    private int caloriesCurrent;
    private Number caloriesGoal;
    private int fatCurrent;
    private Number fatGoal;
    private int carbsCurrent;
    private Number carbsGoal;
    private int proteinCurrent;
    private Number proteinGoal;

    //Food List
    private List<FoodItem> FoodList;

    //Macro Title Text Views
    private TextView textView_title_calories;
    private TextView textView_title_fat;
    private TextView textView_title_carbs;
    private TextView textView_title_protein;

    //Macro Count Text Views
    private TextView textView_calories;
    private TextView textView_fat;
    private TextView textView_carbs;
    private TextView textView_protein;

    //Progress Bars
    private ProgressBar bar_calories;
    private ProgressBar bar_carbs;
    private ProgressBar bar_fat;
    private ProgressBar bar_protein;

    //Goal Buttons
    private EditText editText_calGoal;
    private EditText editText_fatGoal;
    private EditText editText_carbGoal;
    private EditText editText_proGoal;

    //Date
    private TextView textView_date;
    private String currentDateFile; //For file naming
    private String currentDateTitle;//For display on the home page
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_pancake);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set up navigation view item selected listener if needed
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    // Handle navigation view item clicks here.
                    int id = menuItem.getItemId();

                    // if-else or switch to handle different menu item clicks
                    // Example:
                    // if (id == R.id.nav_home) {
                    //     // Handle the home action
                    // } else if (id == R.id.nav_gallery) {
                    //     // Handle the gallery action
                    // }

                    // After handling navigation view item clicks,
                    // you should close the drawer
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
        );
         */

        //Assign Title Text Views
        textView_title_calories = findViewById(R.id.textView_calories);
        textView_title_carbs = findViewById(R.id.textView_carbs);
        textView_title_fat = findViewById(R.id.textView_fat);
        textView_title_protein = findViewById(R.id.textView_protein);

        //Assign Count Text Views
        textView_calories = findViewById(R.id.textView_calroieCount);
        textView_carbs = findViewById(R.id.textView_carbCount);
        textView_fat = findViewById(R.id.textView_fatCount);
        textView_protein = findViewById(R.id.textView_proteinCount);

        //Assign Button Views
        editText_calGoal = findViewById(R.id.editText_goal_calories);
        editText_carbGoal = findViewById(R.id.editText_goal_carbs);
        editText_fatGoal = findViewById(R.id.editText_goal_fat);
        editText_proGoal = findViewById(R.id.editText_goal_protein);

        //Assign Progress Bars
        bar_calories = findViewById(R.id.progressBar_calories);
        bar_carbs = findViewById(R.id.progressBar_carbs);
        bar_fat = findViewById(R.id.progressBar_fat);
        bar_protein = findViewById(R.id.progressBar_protein);

        //Initialize Macro Counts to zero
        caloriesCurrent = 0;
        fatCurrent = 0;
        carbsCurrent = 0;
        proteinCurrent = 0;

        //Initialize FoodList
        FoodList = new ArrayList<>();

        //Init Calendar
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatTitle = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        currentDateFile = dateFormatFile.format(calendar.getTime());
        currentDateTitle = dateFormatTitle.format(calendar.getTime());
        textView_date = findViewById(R.id.textViewDate);
        textView_date.setText(currentDateTitle);

        //Button Listener to Swap to the Food List Activity
        Button foodListButton = findViewById(R.id.button_foodList);
        foodListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
                startActivity(intent);
            }
        });

        // TESTING                                                   <---- TEST
        caloriesGoal = 200;
        fatGoal = 200;
        carbsGoal = 3000;
        proteinGoal = 150;

        //test items
        FoodItem item1 = new FoodItem("item1", 20, 100, 200, 200, 1);
        FoodItem item2 = new FoodItem("item2", 0, 0, 0, 0, 2);
        FoodItem item3 = new FoodItem("item3", 20, 20, 20, 20, 0);
        FoodItem item4 = new FoodItem("item4", 40, 50, 50, 100, 2);
        FoodList.add(item1);
        FoodList.add(item2);
        FoodList.add(item3);
        FoodList.add(item4);
        calculateMacros(); //THIS IS ALSO PART OF A TEST

        //Test File Open/Creation
        Log.d("test", "Test Message");
        retrieveSavedData();

        //                                                          <---- END TEST


        goalListeners();
        updateGoalBars();
    }

    //Attempts to read a food list file and creates one if none exist
    private void retrieveSavedData(){

        //Attempt to open the saved data file for today's date
        String filename = "foodlist-" + currentDateFile + ".txt";

        //Test File Contents
        String fileContents = "New Content";

        //Writes to existing file or makes one if none exist
        //To view File Contents: View-> Tool Windows -> Device File Explorer
        //File Path: data/data/com.example.macromap/files
        FileOutputStream outputStream;
        try {
            // Create a file in the internal storage directory
            File file = new File(getFilesDir(), filename);
            outputStream = new FileOutputStream(file);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateGoalBars() {

        //update current macro counts
        textView_calories.setText(String.valueOf(caloriesCurrent));
        textView_carbs.setText(String.valueOf(carbsCurrent));
        textView_fat.setText(String.valueOf(fatCurrent));
        textView_protein.setText(String.valueOf(proteinCurrent));

        //update goal macro display
        editText_calGoal.setText(String.valueOf(caloriesGoal));
        editText_carbGoal.setText(String.valueOf(carbsGoal));
        editText_fatGoal.setText(String.valueOf(fatGoal));
        editText_proGoal.setText(String.valueOf(proteinGoal));

        //update bars
        bar_calories.setMax(caloriesGoal.intValue());
        bar_calories.setProgress(caloriesCurrent);
        bar_carbs.setMax(carbsGoal.intValue());
        bar_carbs.setProgress(carbsCurrent);
        bar_fat.setMax(fatGoal.intValue());
        bar_fat.setProgress(fatCurrent);
        bar_protein.setMax(proteinGoal.intValue());
        bar_protein.setProgress(proteinCurrent);

        //Toggles the progress bar and font size of Macro counts when goal is present/missing
        toggleMacroDisplay(caloriesGoal.intValue() != 0, bar_calories, textView_title_calories, textView_calories, editText_calGoal);
        toggleMacroDisplay(carbsGoal.intValue() != 0, bar_carbs, textView_title_carbs, textView_carbs, editText_carbGoal);
        toggleMacroDisplay(fatGoal.intValue() != 0, bar_fat, textView_title_fat, textView_fat, editText_fatGoal);
        toggleMacroDisplay(proteinGoal.intValue() != 0, bar_protein, textView_title_protein, textView_protein, editText_proGoal);
    }

    //Calculate the new totals for Macros
    private void calculateMacros(){

        //Zero Out Macros
        caloriesCurrent = 0;
        fatCurrent = 0;
        proteinCurrent = 0;
        carbsCurrent = 0;

        //Un-rounded float totals of the Macros in the food list
        float calAdj = 0;
        float fatAdj = 0;
        float carbAdj = 0;
        float proAdj = 0;

        //Iterate through foodlist and total the macros
        for (FoodItem item : FoodList) {
            calAdj += item.getCaloriesAdj();
            fatAdj += item.getFatAdj();
            carbAdj += item.getCarbsAdj();
            proAdj += item.getProteinAdj();
        }

        //Round total serving-adjusted macros and assign them
        caloriesCurrent = round(calAdj);
        fatCurrent = round(fatAdj);
        proteinCurrent = round(proAdj);
        carbsCurrent = round(carbAdj);
    }

    //listeners for the goal edit texts
    private void goalListeners(){
        editText_calGoal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(textEmpty(editText_calGoal)){
                        caloriesGoal = 0;
                    }
                    else{
                        caloriesGoal = Integer.valueOf(String.valueOf(editText_calGoal.getText()));
                    }
                    updateGoalBars();
                }
                else{
                    editText_calGoal.getText().clear();
                }
            }
        });

        editText_carbGoal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(textEmpty(editText_carbGoal)){
                        carbsGoal = 0;
                    }
                    else{
                        carbsGoal = Integer.valueOf(String.valueOf(editText_carbGoal.getText()));
                    }
                    updateGoalBars();
                }
                else{
                    editText_carbGoal.getText().clear();
                }
            }
        });

        editText_fatGoal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(textEmpty(editText_fatGoal)){
                        fatGoal = 0;
                    }
                    else{
                        fatGoal = Integer.valueOf(String.valueOf(editText_fatGoal.getText()));
                    }
                    updateGoalBars();
                }
                else{
                    editText_fatGoal.getText().clear();
                }
            }
        });

        editText_proGoal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(textEmpty(editText_proGoal)){
                        proteinGoal = 0;
                    }
                    else{
                        proteinGoal = Integer.valueOf(String.valueOf(editText_proGoal.getText()));
                    }
                    updateGoalBars();
                }
                else{
                    editText_proGoal.getText().clear();
                }
            }
        });
    }

    //Checks if an Edit Text element is empty (empty string)
    private boolean textEmpty(EditText text){
        String query = String.valueOf(text.getText());
        if(query.equals("")){return true;}
        else{return false;}
    }

    private void toggleMacroDisplay(Boolean reveal, ProgressBar bar, TextView macroName, TextView macroCount, EditText goal){
        //Reveal the progress bar and shrink marco texts
        if(reveal){
            bar.setVisibility(View.VISIBLE);
            macroName.setTextSize(16);
            macroCount.setTextSize(16);
        }
        //Hide the progress bar and enlarge macro texts
        else{
            bar.setVisibility(View.INVISIBLE);
            macroName.setTextSize(32);
            macroCount.setTextSize(32);
            goal.setText("Add Goal");
        }
    }

    //Food Item
    public static class FoodItem {

        //Macros
        private int calories;
        private int protein;
        private int fat;
        private int carbs;

        private String name;
        private float servings;

        //Constructor
        public FoodItem(String name, int cal, int fat, int carb, int pro, float servings){
            this.name = name;
            this.calories = cal;
            this.fat = fat;
            this.carbs = carb;
            this.protein = pro;
            this.servings = servings;
        }

        //Getters
        public String getName(){return name;}
        public float getServings(){return servings;}

        //Macro Getters
        public int getCalories(){return calories;}
        public int getProtein(){return protein;}
        public int getFat(){return fat;}
        public int getCarbs(){return carbs;}

        //Macro Getters Adjusted for Serving Size
        public float getCaloriesAdj(){return (float)calories * servings;}
        public float getProteinAdj(){return (float)protein * servings;}
        public float getFatAdj(){return (float)fat * servings;}
        public float getCarbsAdj(){return (float)carbs * servings;}
    }


}