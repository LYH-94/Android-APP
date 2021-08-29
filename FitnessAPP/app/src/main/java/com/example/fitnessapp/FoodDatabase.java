package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodDatabase extends AppCompatActivity {

    Button button_back_FoodDatabase, button_addFood_FoodDatabase;
    Intent intent_FoodDatabase;
    ListView listView_FoodDatabase;

    //資料庫部分
    SQLiteDatabase db;
    String str_dbName_FoodDatabase = "SQL_db", str_tbNname_FoodDatabase = "SQL_tb_food", str_createTable_FoodDatabase = "CREATE TABLE IF NOT EXISTS " + str_tbNname_FoodDatabase +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, SQL_name VARCHAR (32), SQL_calories VARCHAR(16), SQL_carbohydrates VARCHAR(16), SQL_protein VARCHAR(16), SQL_fat VARCHAR(16))";
    //資料庫部分---

    Cursor cursor_FoodDatabase;
    int int_rowsNum_FoodDatabase = 0;
    String[] str_listName_FoodDatabase;
    String[] str_listCalories_FoodDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddatabase);

        button_back_FoodDatabase = (Button) findViewById(R.id.button_back_FoodDatabase);
        button_addFood_FoodDatabase = (Button) findViewById(R.id.button_addFood_FoodDatabase);
        button_back_FoodDatabase.setOnClickListener(Button_Listener);
        button_addFood_FoodDatabase.setOnClickListener(Button_Listener);

        db = openOrCreateDatabase(str_dbName_FoodDatabase , Context.MODE_PRIVATE, null);
        db.execSQL(str_createTable_FoodDatabase);

        cursor_FoodDatabase = db.query(str_tbNname_FoodDatabase, null, null, null, null, null, null);
        int_rowsNum_FoodDatabase = cursor_FoodDatabase.getCount();

        str_listName_FoodDatabase = new String[int_rowsNum_FoodDatabase];
        str_listCalories_FoodDatabase = new String[int_rowsNum_FoodDatabase];
        if(int_rowsNum_FoodDatabase != 0){
            cursor_FoodDatabase.moveToFirst();
            for(int i = 0;i < int_rowsNum_FoodDatabase;i++){
                str_listName_FoodDatabase[i] = cursor_FoodDatabase.getString(1);
                str_listCalories_FoodDatabase[i] = cursor_FoodDatabase.getString(2);
                cursor_FoodDatabase.moveToNext();
            }
        }
        cursor_FoodDatabase.close();
        db.close();

        //List選單
        listView_FoodDatabase = (ListView)findViewById(R.id.listView_FoodDatabase);
        ArrayList<HashMap<String,String>> listAdapter = new ArrayList<HashMap<String,String>>();

        //把資料加入ArrayList中
        for(int i=0; i<str_listName_FoodDatabase.length; i++){
            HashMap<String,String> item = new HashMap<String,String>();
            item.put( "str_listCalories_FoodDatabase", str_listCalories_FoodDatabase[i]);
            item.put( "str_listName_FoodDatabase",str_listName_FoodDatabase[i] );
            listAdapter.add( item );
        }

        //新增SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listAdapter,
                android.R.layout.simple_list_item_2,
                new String[] {"str_listName_FoodDatabase","str_listCalories_FoodDatabase"},
                new int[] { android.R.id.text1, android.R.id.text2 } );

        //ListActivity設定adapter
        listView_FoodDatabase.setAdapter(adapter);
        //啟用按鍵過濾功能，這兩行資料都會進行過濾
        listView_FoodDatabase.setTextFilterEnabled(true);

        //List選單事件
        listView_FoodDatabase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FoodDatabase.this, "你選擇的是" + str_listName_FoodDatabase[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Button.OnClickListener Button_Listener = new Button.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_addFood_FoodDatabase:
                    intent_FoodDatabase = new Intent();
                    intent_FoodDatabase.setClass(FoodDatabase.this,AddFoodActivity.class);
                    Toast.makeText(FoodDatabase.this, "addFood~", Toast.LENGTH_SHORT).show();
                    startActivity(intent_FoodDatabase);
                    finish();
                    break;
                case R.id.button_back_FoodDatabase:
                    intent_FoodDatabase = new Intent();
                    intent_FoodDatabase.setClass(FoodDatabase.this,MainActivity.class);
                    startActivity(intent_FoodDatabase);
                    finish();
                    break;
            }
        }
    };
}
