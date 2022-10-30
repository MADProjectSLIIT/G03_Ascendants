package com.example.petpal.ui.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.petpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateCardActivity extends AppCompatActivity {

    String CardID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_card);

        Intent intent = getIntent();
        CardID= intent.getStringExtra("CardID");
        loadData();
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

            db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("CardDetails").document(CardID)
                    .update(cardDetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(UpdateCardActivity.this, PaymentListingActivity.class));
                        }
                    });
        }
    }

    private void loadData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Users").document(user.getUid()).collection("CardDetails").document(CardID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        ((EditText)findViewById(R.id.editTextTextCardNumber)).setText(document.getString("cardNo"));
                        ((EditText)findViewById(R.id.editTextTextExDate)).setText(document.getString("exDate"));
                        ((EditText)findViewById(R.id.editTextTextCVC)).setText(document.getString("cvc"));
                    }
                });
    }
}