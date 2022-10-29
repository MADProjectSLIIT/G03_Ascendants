package com.example.petpal.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.petpal.LoginActivity;
import com.example.petpal.R;
import com.example.petpal.ui.Gigs.MyGigsActivity;
import com.example.petpal.ui.myPets.MyPetsActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    Button btnLogOut;
    FirebaseAuth mAuth;
    MaterialCardView cardMyPets,cardMyGigs;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        cardMyPets = view.findViewById(R.id.cardMyPets);
        cardMyGigs= view.findViewById(R.id.cardMyGigs);


        mAuth = FirebaseAuth.getInstance();
        btnLogOut.setText("LogOut from : "+mAuth.getCurrentUser().getDisplayName());

        cardMyPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyPetsActivity.class));
            }
        });

        cardMyGigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyGigsActivity.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return view;
    }


}