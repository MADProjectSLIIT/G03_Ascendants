package com.example.petpal.ui.myPets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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


public class MyPetsActivity extends AppCompatActivity {
    private Button btnAddPet;
    private RecyclerView petsRecyclerView;
    private PetRecViewAdapter adapter;

    private DatabaseReference dbRef;
    private static final String TAG = "MyPetsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        dbRef=FirebaseDatabase.getInstance("https://petpal-707f9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User Data").child(user.getUid()).child("Pets");
        btnAddPet= findViewById(R.id.btnAddPet);
        petsRecyclerView=findViewById(R.id.petsRecyclerView);

        adapter = new PetRecViewAdapter(this);
        petsRecyclerView.setAdapter(adapter);
        petsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));


        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPetsActivity.this,AddNewPetActivity.class));
            }
        });


//        pets.add(new Pet("what","type","what","helw"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Pet> pets = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot petSnap: dataSnapshot.getChildren()) {
                    Pet p = new Pet();
                    p.setName(petSnap.child("name").getValue().toString());
                    pets.add(p);
                }
                adapter.setPets(pets);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}