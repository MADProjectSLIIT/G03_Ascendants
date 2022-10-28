package com.example.petpal.ui.Explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.petpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
//                                ((TextView)findViewById(R.id.textviewPetTypes)).setText(document.getString("typeOfPet"));
                                ((TextView) findViewById(R.id.textviewDescription)).setText(document.getString("description"));
                                ((TextView) findViewById(R.id.textviewPetSize)).setText(document.getString("size"));
                                ((TextView) findViewById(R.id.textViewNumberOfPets)).setText(document.getLong("noOfPets").toString());
                            } else {
                            }
                        }
                    }
                });

    }
}