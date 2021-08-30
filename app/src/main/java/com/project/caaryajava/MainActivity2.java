package com.project.caaryajava;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Permission;
import java.util.ArrayList;

import static android.widget.Toast.*;

public class MainActivity2 extends AppCompatActivity {
    Button submit, cancel;
    EditText productName;
    EditText marketPrice;
    EditText storePrice;
    EditText category;
    EditText description;
    ImageView productImage;
    Integer position;

    String url;
    ArrayList<Model> list;
    SharedPreferences sharedPreferences;

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
            Uri imageUri = data.getData();
            System.out.println(data + "mmm.....");
            url = imageUri.toString();
            Glide.with(getApplicationContext()).load(url).into(productImage);



    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        productName = findViewById(R.id.product);
        marketPrice = findViewById(R.id.market_p);
        storePrice = findViewById(R.id.store_p);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        productImage = findViewById(R.id.product_img);


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.app_main));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_costomized);
        getSupportActionBar().setElevation(0);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        if(position != -1){
            Model modelItem = (Model) intent.getSerializableExtra("list_item");
            System.out.println(modelItem+"pppppppppppppppooooooooottttnn");
            setValues(modelItem);
        }


        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity2.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }else{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture..."), 1000);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill Product Name", Toast.LENGTH_SHORT).show();
                } else if(marketPrice.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Please fill Market Price", Toast.LENGTH_SHORT).show();
                else if(storePrice.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Please fill Store Price", Toast.LENGTH_SHORT).show();
                else if(category.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Please fill Category", Toast.LENGTH_SHORT).show();
                else if(description.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Please fill Description", Toast.LENGTH_SHORT).show();
                else if(url == null){
                    Toast.makeText(getApplicationContext(), "Please Select an image", Toast.LENGTH_SHORT).show();
                }
                else{

                    String listName = getListName(category.getText().toString());
                    if(position != -1){
                        editItem(listName);
                    }else{
                        addElement(listName);
                    }
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void editItem(String listName) {

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Model>>() {}.getType();

        sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        String jsonText = sharedPreferences.getString(listName, null);



        list = gson.fromJson(jsonText, type);
        System.out.println(position+"pppppppppppppppooooooooottttnn");
        list.set(position, new Model(productName.getText().toString(),
                Integer.parseInt(marketPrice.getText().toString()),
                Integer.parseInt(storePrice.getText().toString()),
                category.getText().toString(),
                description.getText().toString(),
                url
        ));

        jsonText = gson.toJson(list);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(listName, jsonText);
        editor.apply();

    }

    private void setValues(Model modelItem) {
        productName.setText(modelItem.getProductName());
        marketPrice.setText(String.valueOf(modelItem.getMarketPrice()));
        storePrice.setText(String.valueOf(modelItem.getStorePrice()));
        category.setText(modelItem.getCategory());
        description.setText(modelItem.getDescription());
        System.out.println(modelItem.getImage()+"pppppppppppppppooooooooottttnn");
        Glide.with(getApplicationContext()).load(modelItem.getImage()).into(productImage);
        url = modelItem.getImage();
    }

    private String getListName(String str) {

        if(str.contains("foot") || str.contains("Foot")){
            return "footwear";

        }else if(str.contains("fashion") || str.contains("Fashion")){
            return "fashion";

        }else if(str.contains("home") || str.contains("Home")){
            return "home";

        }else{
            return "other";
        }
    }

    private void addElement(String str) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Model>>() {}.getType();

        sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        String jsonText = sharedPreferences.getString(str, null);


        if(jsonText != null) {
            list = gson.fromJson(jsonText, type);
        }
        else{
            list = new ArrayList<Model>();
        }

        list.add(new Model(productName.getText().toString(),
                Integer.parseInt(marketPrice.getText().toString()),
                Integer.parseInt(storePrice.getText().toString()),
                category.getText().toString(),
                description.getText().toString(),
                url
                ));

        jsonText = gson.toJson(list);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(str, jsonText);
        editor.apply();
    }
}