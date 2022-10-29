package com.example.petpal.ui.Explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.petpal.R;
import com.example.petpal.ui.Gigs.Gigs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FilteredByPetTypeActivity extends AppCompatActivity {
    private static final String TAG = "FilteredByServiceActiv";
    private  String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.petpal.R.layout.activity_filtered_by_pet_type);
        Intent intent = getIntent();
        type = intent.getStringExtra("PetType");
        if(!TextUtils.isEmpty(type)){
            ((TextView)findViewById(R.id.textViewSort12)).setText("Sorted By : "+type);
            loadData();
        }

    }

    private void loadData() {

        GigRecViewAdapter adapter= new GigRecViewAdapter(FilteredByPetTypeActivity.this);
        RecyclerView RecyclerViewGigs= findViewById(R.id.recyclerViewRT);
        RecyclerViewGigs.setAdapter(adapter);
        RecyclerViewGigs.setLayoutManager(new LinearLayoutManager(FilteredByPetTypeActivity.this));


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Gigs> gigs=new ArrayList<>();

        db.collection("Gigs").whereArrayContains("typeOfPet",type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //TODO set all data
                                Gigs g = new Gigs();
                                g.setTitle(document.getString("title"));
                                g.setCharge(document.getLong("charge").intValue());
                                g.setGigId(document.getId());
                                gigs.add(g);
                            }
                            adapter.setGigs(gigs);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}