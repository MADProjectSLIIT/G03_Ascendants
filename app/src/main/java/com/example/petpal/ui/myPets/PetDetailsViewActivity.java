package com.example.petpal.ui.myPets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.petpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PetDetailsViewActivity extends AppCompatActivity {
    private static final String TAG = "PetDetailsViewActivity";
    String petId;
    TextView textViewPetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details_view);
        Intent intent = getIntent();

        init();
        if(null!=intent){
            petId = intent.getStringExtra("PetId");
            Log.d(TAG, "onCreate: "+petId);
        }
        loadData();
    }

    private void init(){
        textViewPetName= findViewById(R.id.textViewPetName);
    }

    private void loadData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Users").document(user.getUid()).collection("Pets").document(petId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                           if(document.exists()){

                               textViewPetName.setText(document.getString("petName"));
                               ((TextView)findViewById(R.id.textViewPetType)).setText(document.getString("petType"));
                               ((TextView)findViewById(R.id.textViewPetBreed)).setText(document.getString("breed"));
                               ((TextView)findViewById(R.id.textViewPetSize)).setText(document.getString("size"));
                               ((TextView)findViewById(R.id.textViewPetType)).setText(document.getString("petType"));
                           }else{

                           }

                        }else{
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
    }
}