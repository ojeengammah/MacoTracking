package com.example.macromap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static FoodItemAdapter adapter;
    private static List<MainActivity.FoodItem> foodItemList;

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_foodlist);


        //Initialize FoodList
        foodItemList = new ArrayList<>();

        // TESTING                                                   <---- TEST

        //test items
        MainActivity.FoodItem item1 = new MainActivity.FoodItem("item1", 20, 100, 200, 200, 1);
        MainActivity.FoodItem item2 = new MainActivity.FoodItem("item2", 0, 0, 0, 0, 2);
        MainActivity.FoodItem item3 = new MainActivity.FoodItem("item3", 20, 20, 20, 20, 0);
        MainActivity.FoodItem item4 = new MainActivity.FoodItem("item4", 40, 50, 50, 100, 2);
        MainActivity.FoodItem item5 = new MainActivity.FoodItem("item5", 20, 100, 200, 200, 1);
        MainActivity.FoodItem item6 = new MainActivity.FoodItem("item6", 0, 0, 0, 0, 2);
        MainActivity.FoodItem item7 = new MainActivity.FoodItem("item7", 20, 20, 20, 20, 0);
        MainActivity.FoodItem item8 = new MainActivity.FoodItem("item8", 40, 50, 50, 100, 2);
        foodItemList.add(item1);
        foodItemList.add(item2);
        foodItemList.add(item3);
        foodItemList.add(item4);
        foodItemList.add(item5);
        foodItemList.add(item6);
        foodItemList.add(item7);
        foodItemList.add(item8);

        //                                                          <---- END TEST

        recyclerView = findViewById(R.id.recyclerView_foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodItemAdapter(foodItemList);
        recyclerView.setAdapter(adapter);
    }

    //Called by the delete button listener in FoodItemAdapter.java
    public static void deleteFoodItem(MainActivity.FoodItem itemToDelete){

        //Find the food item being referenced and remove it from the list
        int index = foodItemList.indexOf(itemToDelete);
        foodItemList.remove(index);

        //update the view with the new list info
        adapter = new FoodItemAdapter(foodItemList);
        recyclerView.setAdapter(adapter);
    }


}
