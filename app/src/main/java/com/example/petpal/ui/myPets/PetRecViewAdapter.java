package com.example.petpal.ui.myPets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpal.R;
import com.example.petpal.ui.Explore.FilteredByPetTypeActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class PetRecViewAdapter extends RecyclerView.Adapter<PetRecViewAdapter.ViewHolder> {
    private static final String TAG = "PetRecViewAdapter";

    ArrayList<Pet> pets = new ArrayList<>();
    private Context mContext;
    private Boolean select;

    public PetRecViewAdapter(Context mContext) {
        this.mContext = mContext;
        select = false;
    }

    public void setSelect(Boolean select) {
        this.select = select;
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
        if(pets.get(position).getType().equals("Cat")){
            holder.imgPet.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.cat));
        }


        if(select){
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FilteredByPetTypeActivity.class);
                    intent.putExtra("PetType",pets.get(position).getType());
                    mContext.startActivity(intent);
                }
            });
        }else {
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,PetDetailsViewActivity.class);
                    intent.putExtra("PetId",pets.get(position).getPetID());
                    mContext.startActivity(intent);
                }
            });
        }


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
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgPet = itemView.findViewById(R.id.imgPet);
            parent = itemView.findViewById(R.id.parent);





        }
    }
}
