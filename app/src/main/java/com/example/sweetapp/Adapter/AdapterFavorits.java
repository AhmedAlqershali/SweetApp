package com.example.sweetapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweetapp.R;
import com.example.sweetapp.database.Database;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.Favorites;
import com.example.sweetapp.ui.listFavoritesFragment.ListFavoritesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterFavorits extends RecyclerView.Adapter<AdapterFavorits.Holder> {
    ArrayList<Favorites> favoritesArrayList;
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

    public AdapterFavorits(ArrayList<Favorites> favoritesArrayList, Context context) {
        this.favoritesArrayList = favoritesArrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favlist_item, null, false);
        Holder holder = new Holder(v);
         gg = parent;

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Favorites fav=favoritesArrayList.get(position);

     Picasso.get().load(fav.getImage()).into(holder.iv_Chalet);


        holder.tv_name_Chalet.setText(fav.getName_Chalet());
        holder.tv_Thenumberofhours_Chalet.setText(fav.getNum_Of_Hours());
        holder.tv_Title_Chalet.setText(fav.getAddress());
        holder.btn_DeleteChalet_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(context);

                Toast.makeText(context, "تمت عملية الحذف من المفضلة", Toast.LENGTH_SHORT).show();
                database.removeFromFavorites(fav.getChaletId());
                notifyDataSetChanged();

            }
        });

//        holder.tv_Salary.setText(fav.getPrice() + "");
//
//
//        holder.rb_Evaluation_Chalet.setRating(Float.parseFloat(fav.getEvaluation_Chalet()));

    }

    @Override
    public int getItemCount() {
        return favoritesArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView iv_Chalet;
        TextView tv_name_Chalet, tv_Salary, tv_Title_Chalet, tv_Thenumberofhours_Chalet;
        RatingBar rb_Evaluation_Chalet;
        FloatingActionButton btn_DeleteChalet_fav;


        public Holder(@NonNull View itemView) {


            super(itemView);
            iv_Chalet = itemView.findViewById(R.id.image_Chaletlist_tenant_fav);


            tv_name_Chalet = itemView.findViewById(R.id.name_chaletlist_tenant_fav);
            tv_Title_Chalet = itemView.findViewById(R.id.Titel_ChaletList_tenant_fav);
            tv_Thenumberofhours_Chalet = itemView.findViewById(R.id.Thenumberofhours_ChaletList_tenant_fav);
            tv_Salary = itemView.findViewById(R.id.Thenumberofhours_ChaletList_tenant_fav);
            rb_Evaluation_Chalet = itemView.findViewById(R.id.ratingBar_Chaletlist_tenant);
            btn_DeleteChalet_fav = itemView.findViewById(R.id.btn_DeleteChalet_fav);


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
