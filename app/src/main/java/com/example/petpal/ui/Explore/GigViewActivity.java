package com.example.petpal.ui.Explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GigViewActivity extends AppCompatActivity {
    private static final String TAG = "GigViewActivity";

    private String gigId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.petpal.R.layout.activity_gig_view);

        Intent intent = getIntent();

        if (null != intent) {
            gigId = intent.getStringExtra("GigId");
            Log.d(TAG, "onCreate: " + gigId);
        }
        loadData();

    }

    private void loadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Gigs").document(gigId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                ((TextView) findViewById(R.id.textViewPrice)).setText(document.getLong("charge").toString());
                                ((TextView) findViewById(R.id.textViewTitle)).setText(document.getString("title"));
                                ((TextView) findViewById(R.id.textviewDescription)).setText(document.getString("description"));
                                ((TextView) findViewById(R.id.textviewPetSize)).setText(document.getString("size"));
                                ((TextView) findViewById(R.id.textViewNumberOfPets)).setText(document.getLong("noOfPets").toString());
                                ((TextView)findViewById(R.id.textviewPetTypes)).setText( TextUtils.join(",", (List<String>) document.get("typeOfPet")));
                                ((TextView) findViewById(R.id.textviewLocation)).setText(document.getString("location"));

                                List<String> type = (List<String>) document.get("service");
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Users").document(document.getString("UserId")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        ((TextView) findViewById(R.id.SitterName)).setText(task.getResult().getString("UserName"));
                                    }
                                });
                                for (String x : type) {
                                    switch (x) {
                                        case "Pet Sitting":
                                            ((MaterialCardView) findViewById(R.id.CardPetSitting)).setVisibility(View.VISIBLE);
                                            break;
                                        case "Walking":
                                            ((MaterialCardView) findViewById(R.id.CardPetWaking)).setVisibility(View.VISIBLE);
                                            break;
                                        case "Training":
                                            ((MaterialCardView) findViewById(R.id.CardTraininig)).setVisibility(View.VISIBLE);
                                            break;
                                        case "Grooming":
                                            ((MaterialCardView) findViewById(R.id.CardpetGoorming)).setVisibility(View.VISIBLE);
                                            break;
                                        case "Emergency Transport":
                                            ((MaterialCardView) findViewById(R.id.CardTaxi)).setVisibility(View.VISIBLE);
                                            break;
                                        default:
                                            Toast.makeText(GigViewActivity.this, "what", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            } else {
                            }
                        }
                    }
                });

    }
}