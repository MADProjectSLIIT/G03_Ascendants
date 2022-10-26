package com.example.petpal.ui.myPets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petpal.LoginActivity;
import com.example.petpal.MainActivity;
import com.example.petpal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewPetActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private EditText editTextTextPetName;
    private RadioGroup rgTypeOfPet;
    private Spinner spinnerBreed,spinnerPetSize;
    private Button btnAddPet;

    private Pet pet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet);

        init();
        
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPet();
            }
        });
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        editTextTextPetName=findViewById(R.id.editTextTextPetName);
        rgTypeOfPet=findViewById(R.id.rgTypeOfPet);
        spinnerBreed=findViewById(R.id.spinnerBreed);
        spinnerPetSize=findViewById(R.id.spinnerPetSize);
        btnAddPet=findViewById(R.id.btnAddPet);
    }

    private void addNewPet(){
        FirebaseUser user = mAuth.getCurrentUser();
        dbRef= FirebaseDatabase.getInstance("https://petpal-707f9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User Data").child(user.getUid()).child("Pets");

        String petName= editTextTextPetName.getText().toString();
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


        if(user!=null){
//            Toast.makeText(AddNewPetActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(petName)){
            Toast.makeText(AddNewPetActivity.this, "Please Enter Pet Name", Toast.LENGTH_SHORT).show();
        }else if(petType=="Unknown"){
            Toast.makeText(AddNewPetActivity.this, "Please Enter Pet Type", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(breed)){
            Toast.makeText(AddNewPetActivity.this, "Please Select a breed", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(size)){
            Toast.makeText(AddNewPetActivity.this, "Please Select a size range", Toast.LENGTH_SHORT).show();
        }else{
            pet = new Pet(petName,petType,breed,size);
            dbRef.push().setValue(pet);
        }
    }
}