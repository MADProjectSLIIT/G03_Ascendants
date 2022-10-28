package com.example.petpal.ui.myPets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petpal.LoginActivity;
import com.example.petpal.MainActivity;
import com.example.petpal.R;
import com.example.petpal.ui.Gigs.AddNewGigActivity;
import com.example.petpal.ui.Gigs.MyGigsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewPetActivity extends AppCompatActivity {
    private static final String TAG = "AddNewPetActivity";

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;


    private TextInputLayout editTextTextPetName;
    private RadioGroup rgTypeOfPet;
    private Spinner spinnerBreed,spinnerPetSize;
    private Button btnAddPet;

    private Pet pet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPet();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(AddNewPetActivity.this,MainActivity.class));

    }

    private void init(){
        db  = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        editTextTextPetName=findViewById(R.id.editTextTextPetName);
        rgTypeOfPet=findViewById(R.id.rgTypeOfPet);
        spinnerBreed=findViewById(R.id.spinnerBreed);
        spinnerPetSize=findViewById(R.id.spinnerPetSize);
        btnAddPet=findViewById(R.id.btnAddPet);
    }
    private boolean validate(){
        String petName= editTextTextPetName.getEditText().getText().toString();
        String breed = spinnerBreed.getSelectedItem().toString();
        String size = spinnerPetSize.getSelectedItem().toString();
        String petType="";
        switch (rgTypeOfPet.getCheckedRadioButtonId()){
            case R.id.rbDog:
                petType="Dog";
                break;
            case R.id.rbCat:
                petType="Cat";
                break;
            case R.id.rbBird:
                petType="Bird";
                break;
            case R.id.rbRabbit:
                petType="Rabbit";
                break;
            case R.id.rbOther:
                petType="Other";
                break;
            default:
                petType="Unknown";
                break;
        }


        if(TextUtils.isEmpty(petName)){
            Toast.makeText(AddNewPetActivity.this, "Please Enter Pet Name", Toast.LENGTH_SHORT).show();
            return false;
        }else if(petType=="Unknown"){
            Toast.makeText(AddNewPetActivity.this, "Please Enter Pet Type", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(breed)){
            Toast.makeText(AddNewPetActivity.this, "Please Select a breed", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(size)){
            Toast.makeText(AddNewPetActivity.this, "Please Select a size range", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void addNewPet(){
        FirebaseUser user = mAuth.getCurrentUser();


        String petName= editTextTextPetName.getEditText().getText().toString();
        String breed = spinnerBreed.getSelectedItem().toString();
        String size = spinnerPetSize.getSelectedItem().toString();
        String petType="";
        switch (rgTypeOfPet.getCheckedRadioButtonId()){
            case R.id.rbDog:
                petType="Dog";
                break;
            case R.id.rbCat:
                petType="Cat";
                break;
            case R.id.rbBird:
                petType="Bird";
                break;
            case R.id.rbRabbit:
                petType="Rabbit";
                break;
            case R.id.rbOther:
                petType="Other";
                break;
            default:
                petType="Unknown";
                break;
        }


       if(validate()){
           Map<String, Object> pets = new HashMap<>();
           pets.put("petName", petName);
           pets.put("breed", breed);
           pets.put("size", size);
           pets.put("petType", petType);


           db.collection("Users").document(user.getUid()).collection("Pets")
                   .add(pets)
                   .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                       @Override
                       public void onSuccess(DocumentReference documentReference) {
                           Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                           startActivity(new Intent(AddNewPetActivity.this, MyPetsActivity.class));
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {

                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.w(TAG, "Error adding document", e);
                       }
                   });
       }
    }


}