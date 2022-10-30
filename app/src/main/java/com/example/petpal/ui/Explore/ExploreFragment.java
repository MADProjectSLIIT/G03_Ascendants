package com.example.petpal.ui.Explore;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petpal.R;
import com.example.petpal.ui.Gigs.Gigs;
import com.example.petpal.ui.Gigs.ProfileGigAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private static final String TAG = "ExploreFragment";

    private ExploreViewModel mViewModel;
    RecyclerView RecyclerViewGigs;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);


        getActivity().setTitle("Explore");

        (view.findViewById(R.id.buttonSelettPet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),SelectPetActivity.class));
            }
        });

        GigRecViewAdapter adapter= new GigRecViewAdapter(getContext());
        RecyclerViewGigs= view.findViewById(R.id.recylerViewGigs);
        RecyclerViewGigs.setAdapter(adapter);
        RecyclerViewGigs.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Gigs> gigs=new ArrayList<>();

        db.collection("Gigs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //TODO set all data
                                Gigs g = new Gigs();
                                g.setTitle(document.getString("title"));
                                g.setCharge(document.getLong("charge").intValue());
                                g.setGigId(document.getId());
                                gigs.add(g);
                            }
                            adapter.setGigs(gigs);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });







        return view;
    }



}