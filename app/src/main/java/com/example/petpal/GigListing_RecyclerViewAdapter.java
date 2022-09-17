package com.example.petpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GigListing_RecyclerViewAdapter extends RecyclerView.Adapter<GigListing_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<GigModel> gigModels;

    public GigListing_RecyclerViewAdapter(Context context, ArrayList<GigModel> gigModels){
        this.context= context;
        this.gigModels = gigModels;
    }
    @NonNull
    @Override
    public GigListing_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //this is where you inflate the layout giving a look to our rows (expanding or something)
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row_for_gig_listing,parent,false);
        return new GigListing_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GigListing_RecyclerViewAdapter.MyViewHolder holder, int position) {
        //assign values to the view we created in the recycler_view_row outfile
        //based on the position of the recycler view
    holder.tvTitle.setText(gigModels.get(position).getTitle());
    holder.tvPrice.setText(gigModels.get(position).getPrice());
    holder.imageView.setImageResource(gigModels.get(position).getImage());


    }

    @Override
    public int getItemCount() {

        return gigModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvTitle,tvPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.glrva_Image);
            tvTitle = itemView.findViewById(R.id.glrva_Title);
            tvPrice = itemView.findViewById(R.id.glrva_Price);
        }

    }
}
