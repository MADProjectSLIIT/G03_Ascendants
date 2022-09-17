package com.example.petpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class GigListingPage extends AppCompatActivity {

    ArrayList<GigModel> gigData = new ArrayList<>();

//    int[] gigImages = {R.drawable.dog_image};
int gigImages = R.drawable.dog_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_listing_page);

        RecyclerView recyclerView = findViewById(R.id.glp_recyclerView);

        setUpGigList();

        GigListing_RecyclerViewAdapter adapter = new GigListing_RecyclerViewAdapter(this,gigData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setUpGigList(){
        String[] gigTitles = getResources().getStringArray(R.array.gig_title);
        String[] gigPrice = getResources().getStringArray(R.array.gig_price);

        for (int i=0; i<gigTitles.length; i++){
            gigData.add(new GigModel (gigTitles[i],gigPrice[i],gigImages));
        }

    }
}