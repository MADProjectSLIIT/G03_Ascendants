package com.example.petpal.ui.Explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.petpal.R;
import com.example.petpal.ui.Gigs.Gigs;
import com.example.petpal.ui.myPets.Pet;
import com.example.petpal.ui.myPets.PetRecViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SelectPetActivity extends AppCompatActivity {
    private static final String TAG = "SelectPetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.petpal.R.layout.activity_select_pet);
    }

    @Override
    protected void onStart() {
        super.onStart();
        RecyclerView RecyclerViewGigs= findViewById(R.id.RVPets);
        PetRecViewAdapter adapter = new PetRecViewAdapter(this);

        RecyclerViewGigs.setAdapter(adapter);
        RecyclerViewGigs.setLayoutManager(new GridLayoutManager(this,2));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Pet> pets = new ArrayList<>();
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
                                p.setType(document.getString("petType"));
                                p.setPetID(document.getId());
                                pets.add(p);
                            }
                            if(pets.isEmpty()){
                                ((ImageView)findViewById(R.id.imageViewNoPetsFound)).setVisibility(View.VISIBLE);
                            }else{
                                ((ImageView)findViewById(R.id.imageViewNoPetsFound)).setVisibility(View.GONE);
                            }
                            adapter.setSelect(Boolean.TRUE);
                            adapter.setPets(pets);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}