package com.example.petpal.ui.Explore;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpal.R;
import com.example.petpal.ui.Gigs.Gigs;
import com.example.petpal.ui.Gigs.ProfileGigAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GigRecViewAdapter extends RecyclerView.Adapter<GigRecViewAdapter.ViewHolder> {

    private static final String TAG = "GigRecViewAdapter";

    ArrayList<Gigs> gigs = new ArrayList<>();
    private Context mContext;

    public GigRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gig_for_main_page,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int p) {
        int position = holder.getAdapterPosition();
        Log.d(TAG, "onBindViewHolder: called");
        holder.textViewGigTitle.setText(gigs.get(position).getTitle());
        holder.textViewPrice.setText(Integer.toString(gigs.get(position).getCharge()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,GigViewActivity.class);
                intent.putExtra("GigId",gigs.get(position).getGigId());
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
        private TextView textViewGigTitle,textViewPrice;

        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGigTitle = itemView.findViewById(R.id.textViewGigTitle);
            textViewPrice= itemView.findViewById(R.id.textViewPrice);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
