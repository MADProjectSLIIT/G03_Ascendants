package com.example.petpal.ui.myPets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.petpal.R;


public class MyPetsActivity extends AppCompatActivity {
    private Button btnAddPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        btnAddPet= findViewById(R.id.btnAddPet);

        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPetsActivity.this,AddNewPetActivity.class));
            }
        });
    }
}