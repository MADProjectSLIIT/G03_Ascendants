package com.example.petpal.ui.Gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.petpal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyGigsActivity extends AppCompatActivity {
    Button btnAddGigs;
    RecyclerView RecyclerViewGigs;
    private ProfileGigAdapter adapter;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private static final String TAG = "MyGigsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.petpal.R.layout.activity_my_gigs);

        mAuth = FirebaseAuth.getInstance();

        dbRef= FirebaseDatabase.getInstance("https://petpal-707f9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Gigs");
        btnAddGigs=findViewById(R.id.btnAddGigs);
        RecyclerViewGigs = findViewById(R.id.RecyclerViewGigs);

        adapter= new ProfileGigAdapter(this);
        RecyclerViewGigs.setAdapter(adapter);
        RecyclerViewGigs.setLayoutManager(new LinearLayoutManager(this));

        btnAddGigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyGigsActivity.this,AddNewGigActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        ArrayList<Gigs> gigs=new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot gigSnap:dataSnapshot.getChildren()) {
                    Gigs g = new Gigs();
//                    Toast.makeText(MyGigsActivity.this, gigSnap.child("title").getValue().toString(), Toast.LENGTH_SHORT).show();
                    g.setTitle(gigSnap.child("title").getValue().toString());
                    gigs.add(g);
                }
                adapter.setGigs(gigs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
//        Log.d(TAG, "onStart: This ran");
//        Gigs g = new Gigs();
//        g.setTitle("what");
//        gigs.add(g);
//        adapter.setGigs(gigs);

    }
}