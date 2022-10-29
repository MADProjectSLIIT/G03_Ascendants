package com.example.petpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petpal.ui.payment.AddNewPaymentsActivity;
import com.example.petpal.ui.payment.PaymentListingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    EditText etRegEmail,edittxtUserName;
    EditText etRegPassword,etRegPasswordConf;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword =findViewById(R.id.etRegPassword);
        tvLoginHere =findViewById(R.id.tvLoginHere);
        btnRegister =findViewById(R.id.btnRegister);
        edittxtUserName =findViewById(R.id.edittxtUserName);
        etRegPasswordConf =findViewById(R.id.etRegPasswordConf);


        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            createUser();
        });

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

        });
    }
    private void createUser(){
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        String userName = edittxtUserName.getText().toString();
        String confPassword = etRegPasswordConf.getText().toString();

        if(TextUtils.isEmpty(userName)){
            edittxtUserName.setError("UserName cannot be empty");
            edittxtUserName.requestFocus();
        } else if(TextUtils.isEmpty((email))){
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        }else if (TextUtils.isEmpty((password))){
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        }else if(TextUtils.isEmpty((confPassword))){
            etRegPasswordConf.setError("Password cannot be empty");
            etRegPasswordConf.requestFocus();
        }else if(!confPassword.equals(password)){
            etRegPasswordConf.setError("Password does not match");
            etRegPasswordConf.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                        user.updateProfile(profileUpdates);
                        Map<String, Object> username = new HashMap<>();
                        username.put("UserName:",userName);

                        FirebaseFirestore db  = FirebaseFirestore.getInstance();
                        db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                .set(username)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else{
                        Toast.makeText(RegisterActivity.this,"Registration Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}