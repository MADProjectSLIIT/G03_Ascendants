package com.example.petpal.ui.Gigs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.petpal.R;

public class MyGigsActivity extends AppCompatActivity {
    Button btnAddGigs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.petpal.R.layout.activity_my_gigs);

        btnAddGigs=findViewById(R.id.btnAddGigs);

        btnAddGigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyGigsActivity.this,AddNewGigActivity.class));
            }
        });

    }
}