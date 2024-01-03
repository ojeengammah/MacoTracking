package com.example.macromap;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FoodItemViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView servings;
    private TextView carbs;
    private TextView fats;
    private TextView calories;
    private TextView protein;
    private Button delete;
    private Button edit;
    private MainActivity.FoodItem foodItem;

    public FoodItemViewHolder(View itemView){
        super(itemView);

        //Assign variables to xml views
        name = itemView.findViewById(R.id.textView_foodItem_name);
        servings = itemView.findViewById(R.id.textView_foodItem_servings);
        carbs = itemView.findViewById(R.id.textView_foodItem_carbs);
        fats = itemView.findViewById(R.id.textView_foodItem_fat);
        calories = itemView.findViewById(R.id.textView_foodItem_calories);
        protein = itemView.findViewById(R.id.textView_foodItem_protein);
        delete = itemView.findViewById(R.id.button_foodItem_delete);
        edit = itemView.findViewById(R.id.button_foodItem_edit);

        //Listener for Delete Button
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FoodListActivity.deleteFoodItem(foodItem);
            }
        });

        //Listener for the Edit Button
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // --> EDIT FUNCTION CALL HERE <--
            }
        });

    }

    public void bind(MainActivity.FoodItem foodItem){

        name.setText(foodItem.getName());
        servings.setText("Servings: " + String.valueOf(foodItem.getServings()));
        carbs.setText("Carbs: " + String.valueOf(foodItem.getCarbs()));
        fats.setText("Fats: " + String.valueOf(foodItem.getFat()));
        calories.setText("Calories: " + String.valueOf(foodItem.getCalories()));
        protein.setText("Protein: " + String.valueOf(foodItem.getProtein()));

        this.foodItem = foodItem;

    }

}
