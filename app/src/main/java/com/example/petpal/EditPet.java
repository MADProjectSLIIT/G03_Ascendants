package com.example.petpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditPet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        Spinner spinnerDogBreeds = findViewById(R.id.ap_breedSpinner2);
        ArrayAdapter<CharSequence> adapterDogBreed=ArrayAdapter.createFromResource(this, R.array.dog_breeds, android.R.layout.simple_spinner_item);
        adapterDogBreed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDogBreeds.setAdapter(adapterDogBreed);

        Spinner spinnerPetSize = findViewById(R.id.ap_petSize2);
        ArrayAdapter<CharSequence> adapterPetSize=ArrayAdapter.createFromResource(this, R.array.pet_sizes, android.R.layout.simple_spinner_item);
        adapterPetSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPetSize.setAdapter(adapterPetSize);
    }
}