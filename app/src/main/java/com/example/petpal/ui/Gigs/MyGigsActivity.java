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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyGigsActivity extends AppCompatActivity {
    Button btnAddGigs;
    RecyclerView RecyclerViewGigs;
    private ProfileGigAdapter adapter;

    private FirebaseAuth mAuth;

    private static final String TAG = "MyGigsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.petpal.R.layout.activity_my_gigs);

        mAuth = FirebaseAuth.getInstance();


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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        ArrayList<Gigs> gigs=new ArrayList<>();

        db.collection("Gigs").whereEqualTo("UserId",user.getUid())
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
                                g.setGigId(document.getId());
                                gigs.add(g);
                            }
                            adapter.setGigs(gigs);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });





//        //this is for realtime database
//
//        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot gigSnap:dataSnapshot.getChildren()) {
//                    Gigs g = new Gigs();
////                    Toast.makeText(MyGigsActivity.this, gigSnap.child("title").getValue().toString(), Toast.LENGTH_SHORT).show();
//                    g.setTitle(gigSnap.child("title").getValue().toString());
//                    gigs.add(g);
//                }
//                adapter.setGigs(gigs);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                  Log.w(TAG, "loadPost:onCancelled", error.toException());
//            }
//        });


    }
}