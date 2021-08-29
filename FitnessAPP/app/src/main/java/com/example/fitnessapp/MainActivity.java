package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int int_targetCalories_MainActivity = 0, int_remainingCalories_MainActivity = 0, int_ingest_MainActivity = 0;
    String str_targetCalories_MainActivity = "", str_ingest_MainActivity = "", str_remainingCalories_MainActivity = "";
    TextView text_targetCalories_MainActivity, text_ingest_MainActivity, text_remainingCalories_MainActivity;
    Button button_edit_MainActivity, button_food_MainActivity;
    Intent intent_MainActivity, intent2_MainActivity;

    //資料庫部分
    SQLiteDatabase db;
    String str_dbName_MainActivity = "SQL_db", str_tbNname_MainActivity = "SQL_tb_personalInformation", str_createTable_MainActivity = "CREATE TABLE IF NOT EXISTS " + str_tbNname_MainActivity +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, SQL_targetCalories VARCHAR (32))";
    //資料庫部分---
    Cursor cursor_MainActivity;
    int int_rowsNum_MainActivity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_targetCalories_MainActivity = (TextView) findViewById(R.id.text_targetCalories_MainActivity);
        text_ingest_MainActivity = (TextView) findViewById(R.id.text_ingest_MainActivity);
        text_remainingCalories_MainActivity = (TextView) findViewById(R.id.text_remainingCalories_MainActivity);
        button_edit_MainActivity = (Button) findViewById(R.id.button_edit_MainActivity);
        button_food_MainActivity = (Button) findViewById(R.id.button_food_MainActivity);
        button_edit_MainActivity.setOnClickListener(ButtonListner);
        button_food_MainActivity.setOnClickListener(ButtonListner);

        db = openOrCreateDatabase(str_dbName_MainActivity , Context.MODE_PRIVATE, null);
        db.execSQL(str_createTable_MainActivity);

        cursor_MainActivity = db.query(str_tbNname_MainActivity, null, null, null, null, null, null);
        int_rowsNum_MainActivity = cursor_MainActivity.getCount();

        if(int_rowsNum_MainActivity != 0){
            cursor_MainActivity.moveToFirst();
            int_targetCalories_MainActivity = Integer.parseInt(cursor_MainActivity.getString(1));
            int_ingest_MainActivity = Integer.parseInt(text_ingest_MainActivity.getText().toString());
            int_remainingCalories_MainActivity = int_targetCalories_MainActivity - int_ingest_MainActivity;

            str_targetCalories_MainActivity = ""+int_targetCalories_MainActivity;
            str_ingest_MainActivity = ""+int_ingest_MainActivity;
            str_remainingCalories_MainActivity = ""+int_remainingCalories_MainActivity;

            text_targetCalories_MainActivity.setText(str_targetCalories_MainActivity);
            text_ingest_MainActivity.setText(str_ingest_MainActivity);
            text_remainingCalories_MainActivity.setText(str_remainingCalories_MainActivity);
        }
        cursor_MainActivity.close();
        db.close();

    }

    private Button.OnClickListener ButtonListner = new Button.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_edit_MainActivity:
                    intent_MainActivity = new Intent();
                    intent_MainActivity.setClass(MainActivity.this,EditActivity.class);
                    startActivity(intent_MainActivity);
                    finish();
                    break;
                case R.id.button_food_MainActivity:
                    intent2_MainActivity = new Intent();
                    intent2_MainActivity.setClass(MainActivity.this,FoodDatabase.class);
                    startActivity(intent2_MainActivity);
                    finish();
                    break;
            }
        }
    };
}
