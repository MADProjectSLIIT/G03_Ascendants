package com.example.petpal.ui.myPets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpal.R;

import java.util.ArrayList;

public class PetRecViewAdapter extends RecyclerView.Adapter<PetRecViewAdapter.ViewHolder> {
    private static final String TAG = "PetRecViewAdapter";

    ArrayList<Pet> pets = new ArrayList<>();
    private Context mContext;

    public PetRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public PetRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pet,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetRecViewAdapter.ViewHolder holder, int p) {
        int position = holder.getAdapterPosition();
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtName.setText(pets.get(position).getName());

    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private ImageView imgPet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgPet = itemView.findViewById(R.id.imgPet);




        }
    }
}
