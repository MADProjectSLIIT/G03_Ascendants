package com.example.petpal.ui.payment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpal.MainActivity;
import com.example.petpal.R;
import com.example.petpal.ui.myPets.EditPetActivity;
import com.example.petpal.ui.myPets.MyPetsActivity;
import com.example.petpal.ui.myPets.Pet;
import com.example.petpal.ui.myPets.PetDetailsViewActivity;
import com.example.petpal.ui.myPets.PetRecViewAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class CardRecViewAdapter extends RecyclerView.Adapter<CardRecViewAdapter.ViewHolder> {
    private static final String TAG = "CardRecViewAdapter";

    ArrayList<CreditCard> cards = new ArrayList<>();
    private Context mContext;

    public CardRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_credit_card,parent,false);
        return new CardRecViewAdapter.ViewHolder(view);
    }

    public void setCards(ArrayList<CreditCard> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int p) {
        int position = holder.getAdapterPosition();
        Log.d(TAG, "onBindViewHolder: called");
        holder.textViewCardNumber.setText(cards.get(position).getTextViewCardNumber());
        holder.textViewexpireDate.setText(cards.get(position).getTextViewexpireDate());
        holder.textViewexpirecvc.setText(cards.get(position).getTextViewexpirecvc());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdateCardActivity.class);
                intent.putExtra("CardID",cards.get(position).getCardId());
                mContext.startActivity(intent);

            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FirebaseFirestore db ;
                FirebaseUser user ;
                db = FirebaseFirestore.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();

                new MaterialAlertDialogBuilder(mContext)
                        .setMessage("Do you want delete this card?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.collection("Users").document(user.getUid()).collection("CardDetails").document(cards.get(position).getCardId())
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


                return false;
            }

        });

//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PetDetailsViewActivity.class);
//                intent.putExtra("PetId",pets.get(position).getPetID());
//                mContext.startActivity(intent);
//            }
//        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCardNumber,textViewexpireDate,textViewexpirecvc;
        MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent= itemView.findViewById(R.id.parent);
            textViewCardNumber= itemView.findViewById(R.id.textViewCardNumber);
            textViewexpireDate= itemView.findViewById(R.id.textViewexpireDate);
            textViewexpirecvc= itemView.findViewById(R.id.textViewexpirecvc);
        }
    }
}
