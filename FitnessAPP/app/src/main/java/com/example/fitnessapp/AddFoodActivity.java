package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddFoodActivity extends AppCompatActivity {

    Button button_back_AddFoodActivity, button_confirm_AddFoodActivity;
    EditText eText_name_AddFoodActivity, eText_calories_AddFoodActivity, eText_carbohydrates_AddFoodActivity, eText_protein_AddFoodActivity, eText_fat_AddFoodActivity;

    //資料庫部分
    SQLiteDatabase db;
    String str_dbName_AddFoodActivity = "SQL_db", str_tbName_AddFoodActivity = "SQL_tb_food", str_createTable_AddFoodActivity = "CREATE TABLE IF NOT EXISTS " + str_tbName_AddFoodActivity +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, SQL_name VARCHAR (32), SQL_calories VARCHAR(16), SQL_carbohydrates VARCHAR(16), SQL_protein VARCHAR(16), SQL_fat VARCHAR(16))";
    ContentValues cv_AddFoodActivity;
    //資料庫部分---

    String str_name_AddFoodActivity = "", str_calories_AddFoodActivity = "", str_carbohydrates_AddFoodActivity = "", str_protein_AddFoodActivity = "", str_fat_AddFoodActivity = "";
    Intent intent_AddFoodActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);

        eText_name_AddFoodActivity = (EditText)findViewById(R.id.eText_name_AddFoodActivity);
        eText_calories_AddFoodActivity = (EditText)findViewById(R.id.eText_calories_AddFoodActivity);
        eText_carbohydrates_AddFoodActivity = (EditText)findViewById(R.id.eText_carbohydrates_AddFoodActivity);
        eText_protein_AddFoodActivity = (EditText)findViewById(R.id.eText_protein_AddFoodActivity);
        eText_fat_AddFoodActivity = (EditText)findViewById(R.id.eText_fat_AddFoodActivity);

        button_back_AddFoodActivity = (Button) findViewById(R.id.button_back_AddFoodActivity);
        button_confirm_AddFoodActivity = (Button) findViewById(R.id.button_confirm_AddFoodActivity);
        button_back_AddFoodActivity.setOnClickListener(Button_Listener);
        button_confirm_AddFoodActivity.setOnClickListener(Button_Listener);

        db = openOrCreateDatabase(str_dbName_AddFoodActivity , Context.MODE_PRIVATE, null);
        db.execSQL(str_createTable_AddFoodActivity);

    }

    private Button.OnClickListener Button_Listener = new Button.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_confirm_AddFoodActivity:
                    str_name_AddFoodActivity = eText_name_AddFoodActivity.getText().toString();
                    str_calories_AddFoodActivity = eText_calories_AddFoodActivity.getText().toString();
                    str_carbohydrates_AddFoodActivity = eText_carbohydrates_AddFoodActivity.getText().toString();
                    str_protein_AddFoodActivity = eText_protein_AddFoodActivity.getText().toString();
                    str_fat_AddFoodActivity = eText_fat_AddFoodActivity.getText().toString();

                    cv_AddFoodActivity = new ContentValues(5);
                    cv_AddFoodActivity.put("SQL_name", str_name_AddFoodActivity);
                    cv_AddFoodActivity.put("SQL_calories", str_calories_AddFoodActivity);
                    cv_AddFoodActivity.put("SQL_carbohydrates", str_carbohydrates_AddFoodActivity);
                    cv_AddFoodActivity.put("SQL_protein", str_protein_AddFoodActivity);
                    cv_AddFoodActivity.put("SQL_fat", str_fat_AddFoodActivity);
                    db.insert(str_tbName_AddFoodActivity, null, cv_AddFoodActivity);
                    db.close();
                    intent_AddFoodActivity = new Intent();
                    intent_AddFoodActivity.setClass(AddFoodActivity.this,FoodDatabase.class);
                    Toast.makeText(AddFoodActivity.this, "insert_OK", Toast.LENGTH_SHORT).show();
                    startActivity(intent_AddFoodActivity);
                    finish();
                    break;
                case R.id.button_back_AddFoodActivity:
                    db.close();
                    intent_AddFoodActivity = new Intent();
                    intent_AddFoodActivity.setClass(AddFoodActivity.this,FoodDatabase.class);
                    startActivity(intent_AddFoodActivity);
                    finish();
                    break;
            }
        }
    };
}
