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
import com.example.petpal.ui.Gigs.Gigs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MyPetsActivity extends AppCompatActivity {
    private Button btnAddPet;
    private RecyclerView petsRecyclerView;
    private PetRecViewAdapter adapter;


    private static final String TAG = "MyPetsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        dbRef=FirebaseDatabase.getInstance("https://petpal-707f9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User Data").child(user.getUid()).child("Pets");
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
//        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot petSnap: dataSnapshot.getChildren()) {
//                    Pet p = new Pet();
//                    p.setName(petSnap.child("name").getValue().toString());
//                    pets.add(p);
//                }
//                adapter.setPets(pets);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Users").document(user.getUid()).collection("Pets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //TODO set all data
                                Pet p = new Pet();
                                p.setName(document.getString("petName"));
                                p.setPetID(document.getId());
                                pets.add(p);
                            }
                            adapter.setPets(pets);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}