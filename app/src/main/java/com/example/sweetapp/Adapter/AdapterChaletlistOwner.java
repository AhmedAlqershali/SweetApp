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
import com.example.sweetapp.model.ChaletListIteamModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterChaletlistOwner extends RecyclerView.Adapter<AdapterChaletlistOwner.Holder> {
    ArrayList<ChaletListIteamModel> adapterIteamChaletLists;
    OnItemClickListener onItemClickListener;
    Context context;
    DatabaseReference ChaletsRef;
    AdapterChaletlistOwner adapter;
    ViewGroup gg;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterChaletlistOwner(ArrayList<ChaletListIteamModel> adapterIteamChaletLists, Context context) {
        this.adapterIteamChaletLists = adapterIteamChaletLists;
        this.context = context;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chaletlist_item, null, false);
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
        holder.tv_Salary.setText(A_I_C.getPrice()+"");
        holder.tv_Thenumberofhours_Chalet.setText(A_I_C.getNum_Of_Hours());
        holder.tv_Title_Chalet.setText(A_I_C.getAddress());
        holder.rb_Evaluation_Chalet.setRating(A_I_C.getEvaluation_Chalet());

//        holder.rb_Evaluation_Chalet.setRating(A_I_C.getEvaluation_Chalet());
        ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App");

        holder.btn_EditChalet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EditChaletActivity.class);
                intent.putExtra("ChaletId",A_I_C.getChaletId());
                intent.putExtra("nameChalet",A_I_C.getName_Chalet());
                context.startActivity(intent);
            }
        });


        holder.btn_DeleteChalet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChaletsRef.child("Chalet")
                        .child(A_I_C.getChaletId())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(context, "تمت عملية حذف الشاليه ", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(context, MainActivity.class);
//                            context.startActivity(intent);
                            notifyDataSetChanged();
                        }


                    }
                });
                notifyDataSetChanged();

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
        FloatingActionButton btn_EditChalet, btn_DeleteChalet;


        public Holder(@NonNull View itemView) {


            super(itemView);
            iv_Chalet = itemView.findViewById(R.id.image_Chaletlist);


            tv_name_Chalet = itemView.findViewById(R.id.name_chaletlist);
            tv_Salary = itemView.findViewById(R.id.salaryChaletlist);
            tv_Title_Chalet = itemView.findViewById(R.id.Titel_ChaletList);
            tv_Thenumberofhours_Chalet = itemView.findViewById(R.id.Thenumberofhours_ChaletList);

            rb_Evaluation_Chalet = itemView.findViewById(R.id.ratingBar_Chaletlist);
            btn_DeleteChalet = itemView.findViewById(R.id.btn_DeleteChalet);
            btn_EditChalet = itemView.findViewById(R.id.btn_EditChalet);

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
