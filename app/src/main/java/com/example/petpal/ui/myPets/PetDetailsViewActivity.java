package com.example.petpal.ui.myPets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class PetDetailsViewActivity extends AppCompatActivity {
    private static final String TAG = "PetDetailsViewActivity";
    String petId;
    TextView textViewPetName;
    Button btnDelete;

    FirebaseFirestore db ;
    FirebaseUser user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details_view);
        Intent intent = getIntent();

        init();
        if (null != intent) {
            petId = intent.getStringExtra("PetId");
            Log.d(TAG, "onCreate: " + petId);
        }
        loadData();


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(PetDetailsViewActivity.this)
                        .setMessage("Do you want delete this pet?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(PetDetailsViewActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.collection("Users").document(user.getUid()).collection("Pets").document(petId)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                startActivity(new Intent(PetDetailsViewActivity.this,MyPetsActivity.class));
                                            }
                                        });
                            }
                        })
                        .show();
            }
        });

        editInit();

    }

    private void init() {
        textViewPetName = findViewById(R.id.textViewPetName);
        btnDelete = findViewById(R.id.btnDelete);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    private void loadData() {

        db.collection("Users").document(user.getUid()).collection("Pets").document(petId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                textViewPetName.setText(document.getString("petName"));
                                ((TextView) findViewById(R.id.textViewGender)).setText(document.getString("gender"));
                                ((TextView) findViewById(R.id.textViewPetType)).setText(document.getString("petType"));
                                ((TextView) findViewById(R.id.textViewPetBreed)).setText(document.getString("breed"));
                                ((TextView) findViewById(R.id.textViewPetSize)).setText(document.getString("size"));
                                ((TextView) findViewById(R.id.textViewPetType)).setText(document.getString("petType"));
                                ((TextView) findViewById(R.id.textViewDob)).setText(document.getString("dob"));
                            } else {

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
    }

    private void editInit(){


        textViewPetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(PetDetailsViewActivity.this);

               EditText editText = new EditText(PetDetailsViewActivity.this);
                editText.setText(textViewPetName.getText().toString());
//                editText.setGravity(Gravity.CENTER);
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) editText.getLayoutParams();
//                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//                params.setMargins(1000,1000,0,0);
//                params.alignWithParent();
//                editText.setLayoutParams(params);

                dialog.setView(editText);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String, Object> pets = new HashMap<>();
                        pets.put("petName", editText.getText().toString());
                        db.collection("Users").document(user.getUid()).collection("Pets").document(petId)
                                .update(pets)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        loadData();
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });

        ((TextView) findViewById(R.id.textViewGender)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(PetDetailsViewActivity.this);

                Spinner sp = new Spinner(PetDetailsViewActivity.this);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PetDetailsViewActivity.this,R.array.pet_gender ,android.R.layout.simple_spinner_item );
                sp.setAdapter(adapter);

                dialog.setView(sp);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String, Object> pets = new HashMap<>();
                        pets.put("gender", sp.getSelectedItem().toString());
                        db.collection("Users").document(user.getUid()).collection("Pets").document(petId)
                                .update(pets)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        loadData();
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });


        ((TextView) findViewById(R.id.textViewPetType)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(PetDetailsViewActivity.this);

                Spinner sp = new Spinner(PetDetailsViewActivity.this);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PetDetailsViewActivity.this,R.array.dog_breeds ,android.R.layout.simple_spinner_item );
                sp.setAdapter(adapter);
//                sp.setPadding(20,120,120,120);
//                sp.setGravity(Gravity.CENTER_VERTICAL);
                dialog.setView(sp);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PetDetailsViewActivity.this, "hmmm", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

    }
}