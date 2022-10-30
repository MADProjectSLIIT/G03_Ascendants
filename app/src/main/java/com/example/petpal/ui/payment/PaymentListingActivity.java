package com.example.petpal.ui.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.petpal.R;
import com.example.petpal.ui.myPets.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PaymentListingActivity extends AppCompatActivity {
    private static final String TAG = "PaymentListingActivity";

    Button btnAddNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_listing);
        btnAddNew = findViewById(R.id.btnAddNew);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentListingActivity.this, AddNewPaymentsActivity.class));
            }
        });
        
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        CardRecViewAdapter adapter = new CardRecViewAdapter(this);
        RecyclerView  cardrecview = findViewById(R.id.cardrecview);
        cardrecview.setAdapter(adapter);
        cardrecview.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<CreditCard> cards = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Users").document(user.getUid()).collection("CardDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                CreditCard p = new CreditCard();
                                p.setTextViewCardNumber(document.getString("cardNo"));
                                p.setTextViewexpireDate(document.getString("exDate"));
                                p.setTextViewexpirecvc(document.getString("cvc"));
                                p.setCardId(document.getId());
                                cards.add(p);
                            }
                            adapter.setCards(cards);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
//        CreditCard p = new CreditCard();
//        p.setTextViewCardNumber("sdfs");
//        p.setTextViewexpireDate("fsdf");
//        p.setTextViewexpirecvc("sdfsdf");
//        cards.add(p);
//        adapter.setCards(cards);
    }
}