package com.example.petpal.ui.Gigs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpal.MainActivity;
import com.example.petpal.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProfileGigAdapter extends RecyclerView.Adapter<ProfileGigAdapter.ViewHolder> {

    private static final String TAG = "ProfileGigAdapter";

    ArrayList <Gigs> gigs = new ArrayList<>();
    private Context mContext;

    public ProfileGigAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gig_profile,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int p) {
        int position = holder.getAdapterPosition();
        Log.d(TAG, "onBindViewHolder: called");
        holder.textViewGigName.setText(gigs.get(position).getTitle());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db ;
                FirebaseUser user ;
                db = FirebaseFirestore.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();

                new MaterialAlertDialogBuilder(mContext)
                        .setMessage("Do you want delete this gig?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.collection("Gigs").document(gigs.get(position).getGigId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                mContext.startActivity(new Intent(mContext, MainActivity.class));
                                            }
                                        });

                            }
                        })
                        .show();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,UpdateGigActivity.class);
                intent.putExtra("GigID",gigs.get(position).getGigId());
                mContext.startActivity(intent);
            }
        });
    }

    public void setGigs(ArrayList<Gigs> gigs) {
        this.gigs = gigs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gigs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewGigName;
        private Button btnDelete,btnUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGigName = itemView.findViewById(R.id.textViewGigName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
