package com.example.petpal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpal.R;
import com.example.petpal.databinding.FragmentHomeBinding;
import com.example.petpal.ui.Explore.FilteredByServiceActivity;
import com.example.petpal.ui.Explore.GigRecViewAdapter;
import com.example.petpal.ui.Gigs.Gigs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";


    private FragmentHomeBinding binding;
    RecyclerView RecyclerViewGigs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerViewGigs=  root.findViewById(R.id.RVNereColombo);

        getActivity().setTitle("Home");

        root.findViewById(R.id.CardPetWaking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FilteredByServiceActivity.class);
                intent.putExtra("Type","Walking");
                startActivity(intent);
            }
        });
        root.findViewById(R.id.CardpetGoorming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "What", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FilteredByServiceActivity.class);
                intent.putExtra("Type","Grooming");
                startActivity(intent);
            }
        });
        root.findViewById(R.id.CardPetTaxi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "What", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FilteredByServiceActivity.class);
                intent.putExtra("Type","Emergency Transport");
                startActivity(intent);
            }
        });
        root.findViewById(R.id.CardPetTrainning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "What", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FilteredByServiceActivity.class);
                intent.putExtra("Type","Training");
                startActivity(intent);
            }
        });
        root.findViewById(R.id.CardPetSitting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "What", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FilteredByServiceActivity.class);
                intent.putExtra("Type","Pet Sitting");
                startActivity(intent);
            }
        });

       

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        GigRecViewAdapter adapter= new GigRecViewAdapter(getContext());

        RecyclerViewGigs.setAdapter(adapter);
        RecyclerViewGigs.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Gigs> gigs=new ArrayList<>();

        db.collection("Gigs").whereEqualTo("location","Colombo")
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
        Log.w(TAG, "Error getting documents.");
    }
}