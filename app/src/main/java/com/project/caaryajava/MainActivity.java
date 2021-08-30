package com.project.caaryajava;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView fab;
    TextView collapse;
    SharedPreferences sharedPreferences;
    ArrayList<Model> footWear, fashion, home, other;
    ImageView footArrow, fashionArrow, homeArrow, otherArrow;
    RecyclerView footWearList, fashionList, homeList, otherList;
    LinearLayout footTV, fashionTV, homeTV, otherTV;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        collapse = findViewById(R.id.collapse);

        footWearList = findViewById(R.id.footwear_list);
        fashionList = findViewById(R.id.fashion_list);
        homeList = findViewById(R.id.home_list);
        otherList = findViewById(R.id.other);

        footArrow = findViewById(R.id.footwear_arrow);
        fashionArrow = findViewById(R.id.fashion_arrow);
        homeArrow = findViewById(R.id.home_arrow);
        otherArrow = findViewById(R.id.other_arrow);

        footTV = findViewById(R.id.footTV);
        fashionTV = findViewById(R.id.fashionTV);
        homeTV = findViewById(R.id.homeTV);
        otherTV = findViewById(R.id.otherTV);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.app_main));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_costomized);
        getSupportActionBar().setElevation(0);

        loadData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footArrow.setRotation(180);
                footWearList.setVisibility(View.GONE);
                fashionArrow.setRotation(180);
                fashionList.setVisibility(View.GONE);
                homeArrow.setRotation(180);
                homeList.setVisibility(View.GONE);
                otherArrow.setRotation(180);
                otherList.setVisibility(View.GONE);
            }
        });
        footTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(footWearList.getVisibility() == View.GONE){
                    footArrow.setRotation(270);
                    footWearList.setVisibility(View.VISIBLE);
                }else {
                    footArrow.setRotation(180);
                    footWearList.setVisibility(View.GONE);
                }
            }
        });
        fashionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fashionList.getVisibility() == View.GONE){
                    fashionArrow.setRotation(270);
                    fashionList.setVisibility(View.VISIBLE);
                }else {
                    fashionArrow.setRotation(180);
                    fashionList.setVisibility(View.GONE);
                }
            }
        });
        homeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeList.getVisibility() == View.GONE){
                    homeArrow.setRotation(270);
                    homeList.setVisibility(View.VISIBLE);
                }else {
                    homeArrow.setRotation(180);
                    homeList.setVisibility(View.GONE);
                }
            }
        });
        otherTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otherList.getVisibility() == View.GONE){
                    otherArrow.setRotation(270);
                    otherList.setVisibility(View.VISIBLE);
                }else {
                    otherArrow.setRotation(180);
                    otherList.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadData() {
        Gson gson = new Gson();
        sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        String jsonText = sharedPreferences.getString("footwear", null);
        Type type = new TypeToken<ArrayList<Model>>() {}.getType();
        if(jsonText != null && !jsonText.equals("")){
            footWear = gson.fromJson(jsonText, type);
            footWearList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//            footWearList.setHasFixedSize(true);
            footWearList.setAdapter(new MyListAdapter(footWear, "footwear", MainActivity.this));

        }
        jsonText = sharedPreferences.getString("fashion", null);
        if(jsonText != null){
            fashion = gson.fromJson(jsonText, type);
            fashionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            fashionList.setAdapter(new MyListAdapter(fashion, "fashion", MainActivity.this));
        }
        jsonText = sharedPreferences.getString("home", null);
        if(jsonText != null){
            home = gson.fromJson(jsonText, type);
            homeList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            homeList.setAdapter(new MyListAdapter(home, "home", MainActivity.this));
        }
        jsonText = sharedPreferences.getString("other", null);
        if(jsonText != null){
            other = gson.fromJson(jsonText, type);
            otherList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            otherList.setAdapter(new MyListAdapter(other, "other", MainActivity.this));
        }
    }


}