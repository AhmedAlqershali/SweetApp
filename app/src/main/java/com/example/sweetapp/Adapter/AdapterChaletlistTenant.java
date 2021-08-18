package com.example.sweetapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweetapp.EditChaletActivity;
import com.example.sweetapp.R;
import com.example.sweetapp.database.Database;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.Favorites;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterChaletlistTenant extends RecyclerView.Adapter<AdapterChaletlistTenant.Holder> {
    ArrayList<ChaletListIteamModel> adapterIteamChaletLists;
    OnItemClickListener onItemClickListener;
    Context context;
    ViewGroup gg;
    Database Db;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterChaletlistTenant(ArrayList<ChaletListIteamModel> adapterIteamChaletLists, Context context) {
        this.adapterIteamChaletLists = adapterIteamChaletLists;
        this.context = context;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenantlist_item, null, false);
        Holder holder = new Holder(v);
         gg = parent;

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ChaletListIteamModel A_I_C=adapterIteamChaletLists.get(position);

//        holder.iv_Chalet.setImageResource(A_I_C.getImg_Chalet());
     Picasso.get().load(A_I_C.getImage()).into(holder.iv_Chalet);


        holder.tv_name_Chalet.setText(A_I_C.getName_Chalet());
        holder.tv_Salary.setText(A_I_C.getPrice() +"$");
        holder.tv_Thenumberofhours_Chalet.setText(A_I_C.getNum_Of_Hours());
        holder.tv_Title_Chalet.setText(A_I_C.getAddress());


        holder.rb_Evaluation_Chalet.setRating(A_I_C.getEvaluation_Chalet());


        Db = new Database(context);
        if (Db.isFavorites(A_I_C.getChaletId()))
            holder.floatingActionButton_Favorites.setImageResource(R.drawable.ic_baseline_favorite_24);
        holder.floatingActionButton_Favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorites favorites = new Favorites();
                favorites.setChaletId(A_I_C.getChaletId());
                favorites.setName_Chalet(A_I_C.getName_Chalet());
                favorites.setImage(A_I_C.getImage());
                favorites.setAddress(A_I_C.getAddress());
                favorites.setNum_Of_Hours(A_I_C.getNum_Of_Hours());
                favorites.setPhone(A_I_C.getPhone());
                favorites.setChaletOwnerId(A_I_C.getChaletOwnerId());
//                favorites.setPrice(A_I_C.getPrice()+"");
//                favorites.setEvaluation_Chalet(A_I_C.getEvaluation_Chalet()+"");

                if (!Db.isFavorites(A_I_C.getChaletId())) {
                    Db.addTOFavorites(favorites);
                    holder.floatingActionButton_Favorites.setImageResource(R.drawable.ic_baseline_favorite_24);
                    Toast.makeText(context, ""+A_I_C.getName_Chalet()+ " was added to Favorites", Toast.LENGTH_SHORT).show();

                }else {
                    Db.removeFromFavorites(A_I_C.getChaletId());
                    holder.floatingActionButton_Favorites.setImageResource(R.drawable.ic_baseline_favorite_border_off);
                    Toast.makeText(context, ""+A_I_C.getName_Chalet()+ " was removed from Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return adapterIteamChaletLists.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView iv_Chalet;
        TextView tv_name_Chalet, tv_Salary, tv_Title_Chalet, tv_Thenumberofhours_Chalet;
        RatingBar rb_Evaluation_Chalet;
        FloatingActionButton floatingActionButton_Favorites;


        public Holder(@NonNull View itemView) {


            super(itemView);
            iv_Chalet = itemView.findViewById(R.id.image_Chaletlist_tenant);


            tv_name_Chalet = itemView.findViewById(R.id.name_chaletlist_tenant);
            tv_Salary = itemView.findViewById(R.id.salaryChaletlist_tenant);
            tv_Title_Chalet = itemView.findViewById(R.id.Titel_ChaletList_tenant);
            tv_Thenumberofhours_Chalet = itemView.findViewById(R.id.Thenumberofhours_ChaletList_tenant);
            floatingActionButton_Favorites = itemView.findViewById(R.id.floatingActionButton_Favorites);

            rb_Evaluation_Chalet = itemView.findViewById(R.id.ratingBar_Chaletlist_tenant);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }

}
