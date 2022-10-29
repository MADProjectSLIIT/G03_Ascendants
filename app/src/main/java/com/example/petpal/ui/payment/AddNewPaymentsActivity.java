package com.example.petpal.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.petpal.R;
import com.example.petpal.ui.myPets.AddNewPetActivity;
import com.example.petpal.ui.myPets.MyPetsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewPaymentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_payments);

        ((Button)findViewById(R.id.btnSaveCard)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
            }
        });
    }

    private void setData(){
        String cardNo =  ((EditText)findViewById(R.id.editTextTextCardNumber)).getText().toString();
        String exDate =  ((EditText)findViewById(R.id.editTextTextExDate)).getText().toString();
        String cvc =  ((EditText)findViewById(R.id.editTextTextCVC)).getText().toString();

        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
if(!TextUtils.isEmpty(cardNo)||!TextUtils.isEmpty(exDate)||!TextUtils.isEmpty(cvc)){
    Map<String, Object> cardDetails = new HashMap<>();
    cardDetails.put("cardNo",cardNo);
    cardDetails.put("exDate",exDate);
    cardDetails.put("cvc",cvc);

    db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("CardDetails")
            .add(cardDetails)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    startActivity(new Intent(AddNewPaymentsActivity.this, PaymentListingActivity.class));
                }
            });
}


    }
}