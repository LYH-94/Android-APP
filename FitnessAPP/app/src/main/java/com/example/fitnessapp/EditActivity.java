package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    int int_calories_EditActivity = 0, int_rowsNum_EditActivity = 0;
    Intent intent_EditActivity;
    EditText etext_targetCalories_EditActivity;
    Button button_cancel_EditActivity, button_confirm_EditActivity;

    //資料庫部分
    SQLiteDatabase db;
    String str_dbName_EditActivity = "SQL_db", str_tbNname_EditActivity = "SQL_tb_personalInformation";
    String str_createTable_EditActivity = "CREATE TABLE IF NOT EXISTS " + str_tbNname_EditActivity +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, SQL_targetCalories VARCHAR (32))";
    //資料庫部分---

    ContentValues cv_EditActivity;
    Cursor cursor_EditActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etext_targetCalories_EditActivity = (EditText)findViewById(R.id.etext_targetCalories_EditActivity);
        button_cancel_EditActivity = (Button) findViewById(R.id.button_cancel_EditActivity);
        button_confirm_EditActivity = (Button) findViewById(R.id.button_confirm_EditActivity);
        button_cancel_EditActivity.setOnClickListener(Button_Listener);
        button_confirm_EditActivity.setOnClickListener(Button_Listener);

        db = openOrCreateDatabase(str_dbName_EditActivity , Context.MODE_PRIVATE, null);
        db.execSQL(str_createTable_EditActivity);

        cursor_EditActivity = db.query(str_tbNname_EditActivity, null, null, null, null, null, null);
        int_rowsNum_EditActivity = cursor_EditActivity.getCount();

        if(int_rowsNum_EditActivity == 0){
            cv_EditActivity = new ContentValues(1);
            cv_EditActivity.put("SQL_targetCalories", int_calories_EditActivity);
            db.insert(str_tbNname_EditActivity, null, cv_EditActivity);
        }
    }

    private Button.OnClickListener Button_Listener = new Button.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_confirm_EditActivity:
                    int_calories_EditActivity = Integer.parseInt(etext_targetCalories_EditActivity.getText().toString());
                    cv_EditActivity = new ContentValues(1);
                    cv_EditActivity.put("SQL_targetCalories", int_calories_EditActivity);
                    db.update(str_tbNname_EditActivity, cv_EditActivity, "_id = 1", null);
                    db.close();
                    intent_EditActivity = new Intent();
                    intent_EditActivity.setClass(EditActivity.this,MainActivity.class);
                    startActivity(intent_EditActivity);
                    finish();
                    break;
                case R.id.button_cancel_EditActivity:
                    db.close();
                    intent_EditActivity = new Intent();
                    intent_EditActivity.setClass(EditActivity.this,MainActivity.class);
                    startActivity(intent_EditActivity);
                    finish();
                    break;
            }
        }
    };
}
