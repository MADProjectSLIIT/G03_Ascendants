package com.example.petpal.ui.Gigs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.petpal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewGigActivity extends AppCompatActivity {

    private static final String TAG = "AddNewGigActivity";

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;
    private CheckBox checkBoxPetSitting,checkBoxWalking,checkBoxTraining,checkBoxGrooming,checkBoxEmergencyTransport;
    private CheckBox checkBoxCat,checkBoxBird,checkBoxOther,checkBoxDog;
    private EditText editTextNumberOfPets,editTextNumberOfDays,editTextPrice,editTextDescription,editTextTitle;
    private  Spinner spinnerPetSize,spinnerTravelDistance,spinnerLocation;
    private Button btnAddGig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_gig);

        init();

        btnAddGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }


    private void init(){

        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();

        checkBoxPetSitting=findViewById(R.id.checkBoxPetSitting);
        checkBoxWalking=findViewById(R.id.checkBoxWalking);
        checkBoxTraining=findViewById(R.id.checkBoxTraining);
        checkBoxGrooming=findViewById(R.id.checkBoxGrooming);
        checkBoxEmergencyTransport=findViewById(R.id.checkBoxEmergencyTransport);

        checkBoxCat=findViewById(R.id.checkBoxCat);
        checkBoxBird=findViewById(R.id.checkBoxBird);
        checkBoxOther=findViewById(R.id.checkBoxOther);
        checkBoxDog=findViewById(R.id.checkBoxDog);

        editTextTitle= findViewById(R.id.editTextTitle);
        editTextNumberOfPets=findViewById(R.id.editTextNumberOfPets);
        editTextNumberOfDays=findViewById(R.id.editTextNumberOfDays);
        editTextPrice=findViewById(R.id.editTextPrice);
        editTextDescription=findViewById(R.id.editTextDescription);


        spinnerPetSize=findViewById(R.id.spinnerPetSize);
        spinnerTravelDistance=findViewById(R.id.spinnerTravelDistance);
        spinnerLocation=findViewById(R.id.spinnerLocation);

        btnAddGig=findViewById(R.id.btnAddGig);
    }

    private void submit(){
        FirebaseUser user = mAuth.getCurrentUser();



        //TODO check if at least one of the check boxes is checked
        ArrayList<String> service = new ArrayList<>() ;
        if(checkBoxPetSitting.isChecked()){
            service.add(checkBoxPetSitting.getText().toString());
        }
        if(checkBoxWalking.isChecked()){
            service.add(checkBoxWalking.getText().toString());
        }
        if(checkBoxTraining.isChecked()){
            service.add(checkBoxTraining.getText().toString());
        }
        if(checkBoxGrooming.isChecked()){
            service.add(checkBoxGrooming.getText().toString());
        }
        if(checkBoxEmergencyTransport.isChecked()){
            service.add(checkBoxEmergencyTransport.getText().toString());
        }


        //TODO check if at least one of the check boxes is checked
        ArrayList<String> typeOfPet = new ArrayList<>() ;
        if(checkBoxCat.isChecked()){
            typeOfPet.add(checkBoxCat.getText().toString());
        }
        if(checkBoxBird.isChecked()){
            typeOfPet.add(checkBoxBird.getText().toString());
        }
        if(checkBoxOther.isChecked()){
            typeOfPet.add(checkBoxOther.getText().toString());
        }
        if(checkBoxDog.isChecked()){
            typeOfPet.add(checkBoxDog.getText().toString());
        }

        String title = editTextTitle.getText().toString();
         int noOfPets = Integer.parseInt(editTextNumberOfPets.getText().toString());
         String size = spinnerPetSize.getSelectedItem().toString();
         int noOfDays = Integer.parseInt(editTextNumberOfDays.getText().toString());
         String travelDistance = spinnerTravelDistance.getSelectedItem().toString();
         int charge  = Integer.parseInt(editTextPrice.getText().toString());
         String location = spinnerLocation.getSelectedItem().toString();
         String description=editTextDescription.getText().toString();


         //TODO improve this to give specific errors


        Map<String, Object> gigs = new HashMap<>();
        gigs.put("UserId", user.getUid());
        gigs.put("title", title);
        gigs.put("service", service);
        gigs.put("typeOfPet", typeOfPet);
        gigs.put("noOfPets", noOfPets);
        gigs.put("size", size);
        gigs.put("noOfDays", noOfDays);
        gigs.put("travelDistance", travelDistance);
        gigs.put("charge", charge);
        gigs.put("location", location);
        gigs.put("description", description);


        db.collection("Gigs")
                .add(gigs)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        startActivity(new Intent(AddNewGigActivity.this,MyGigsActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });



//        Gigs newGig = new Gigs(user.getUid(),title,service,typeOfPet,noOfPets,size,noOfDays,travelDistance,charge,location,description);
//        dbRef.push().setValue(newGig);
//        startActivity(new Intent(AddNewGigActivity.this,MyGigsActivity.class));
    }
}