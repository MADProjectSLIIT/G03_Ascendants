package com.example.petpal.ui.myPets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


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


import com.example.petpal.MainActivity;
import com.example.petpal.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddNewPetActivity extends AppCompatActivity {
    private static final String TAG = "AddNewPetActivity";

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;


    private TextInputLayout editTextTextPetName;
    private TextInputEditText editTextDate;
    private RadioGroup rgTypeOfPet;
    private Spinner spinnerBreed,spinnerPetSize,spinnerGender;
    private Button btnAddPet;

    private Pet pet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPet();
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
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
        editTextDate= findViewById(R.id.editTextDate);
        rgTypeOfPet=findViewById(R.id.rgTypeOfPet);
        spinnerBreed=findViewById(R.id.spinnerBreed);
        spinnerPetSize=findViewById(R.id.spinnerPetSize);
        spinnerGender=findViewById(R.id.spinnerGender);
        btnAddPet=findViewById(R.id.btnAddPet);
    }
    private boolean validate(String petName,String breed,String size,String gender,String dob,String petType){



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
        }else if(TextUtils.isEmpty(gender)){
            Toast.makeText(AddNewPetActivity.this, "Please Select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(dob)){
            Toast.makeText(AddNewPetActivity.this, "Please Select date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }


    private void selectDate() {
       MaterialDatePicker datePicker =  MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Birth Date").build();
        datePicker.show(getSupportFragmentManager(),"what");
        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
            String date  = sdf.format(selection);
            editTextDate.setText(date);
        });


    }


    private void addNewPet(){
        FirebaseUser user = mAuth.getCurrentUser();


        String petName= editTextTextPetName.getEditText().getText().toString();
        String breed = spinnerBreed.getSelectedItem().toString();
        String size = spinnerPetSize.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        String dob= editTextDate.getText().toString();

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


       if(validate(petName,breed,size,gender,dob,petType)){
           Map<String, Object> pets = new HashMap<>();
           pets.put("petName", petName);
           pets.put("breed", breed);
           pets.put("size", size);
           pets.put("petType", petType);
           pets.put("dob", dob);
           pets.put("gender", gender);


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