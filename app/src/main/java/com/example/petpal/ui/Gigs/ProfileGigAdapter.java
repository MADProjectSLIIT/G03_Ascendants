package com.example.petpal.ui.Gigs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpal.R;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGigName = itemView.findViewById(R.id.textViewGigName);
        }
    }
}
