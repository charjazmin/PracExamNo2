package com.real.francisamiel.pracexamno2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText editName, editAge, editGender;
    ArrayList<String> listPpl;
    ArrayAdapter adapter;
    ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button) findViewById(R.id.btn_save);
        btnViewData = (Button) findViewById(R.id.btn_search);
        editName = (EditText) findViewById(R.id.txtFullname) ;
        editAge = (EditText) findViewById(R.id.txtAge);
        editGender = (EditText) findViewById(R.id.txtGender);
        mDatabaseHelper = new DatabaseHelper(this);
        listPpl = new ArrayList<>();
        userlist = findViewById(R.id.mainlist);
        viewData();

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlist.setVisibility(View.VISIBLE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editName.getText().toString();
                String newAge = editAge.getText().toString();
                String newGender = editGender.getText().toString();
                if (editName.length() != 0 && editAge.length() != 0 && editGender.length() != 0) {
                    AddData(newName);
                    AddData(newAge);
                    AddData(newGender);
                    editName.setText("");
                    editAge.setText("");
                    editGender.setText("");
                } else {
                    Toast.makeText(MainActivity.this,"You must put something in the text field!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(editName.getText().toString(), editAge.getText().toString(),editGender.getText().toString());

        if (insertData) {
            Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
        }
    }

    private void viewData(){
        Cursor cursor = mDatabaseHelper.getData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data to Show", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                listPpl.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listPpl);
            userlist.setAdapter(adapter);
        }
    }
}
