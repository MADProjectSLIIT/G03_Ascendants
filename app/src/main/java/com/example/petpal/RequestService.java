package com.example.petpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RequestService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        Spinner spinnerSelectPet = findViewById(R.id.spinnerSelectYourPet);
        ArrayAdapter<CharSequence> adapterSelectPet=ArrayAdapter.createFromResource(this, R.array.select_pet, android.R.layout.simple_spinner_item);
        adapterSelectPet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectPet.setAdapter(adapterSelectPet);

        Spinner spinnerLocation = findViewById(R.id.spinnerLocation);
        ArrayAdapter<CharSequence> adapterSpinnerLocation=ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        adapterSpinnerLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapterSpinnerLocation);

        Spinner spinnerDays = findViewById(R.id.spinnerDays);
        ArrayAdapter<CharSequence> adapterDays=ArrayAdapter.createFromResource(this, R.array.no_of_days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapterDays);
    }
}