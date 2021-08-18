package com.example.sweetapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweetapp.R;
import com.example.sweetapp.model.Request;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AdapterRervation extends RecyclerView.Adapter<AdapterRervation.Holder> {
    ArrayList<Request> adapterIteamChaletLists;
    OnItemClickListener onItemClickListener;
    Context context;
    DatabaseReference ChaletsRef;
    AdapterRervation adapter;
    ViewGroup gg;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterRervation(ArrayList<Request> adapterIteamChaletLists, Context context) {
        this.adapterIteamChaletLists = adapterIteamChaletLists;
        this.context = context;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_layout, null, false);
        Holder holder = new Holder(v);
         gg = parent;

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Request A_I_C=adapterIteamChaletLists.get(position);

        int p = position + 1;
        holder.txt_key.setText("#"+p);
        holder.tv_name_chaletRequired.setText(A_I_C.getName_chalet()+"");
        holder.txt_bookingDate.setText(A_I_C.getBooking_date());
        holder.txt_bookingPeriod.setText(A_I_C.getBooking_period());
        holder.txt_status.setText(A_I_C.getStatus());

    }

    @Override
    public int getItemCount() {
        return adapterIteamChaletLists.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txt_key,tv_name_chaletRequired, txt_bookingDate, txt_bookingPeriod,txt_status;



        public Holder(@NonNull View itemView) {


            super(itemView);
            txt_key = itemView.findViewById(R.id.txt_key_reservation);
            tv_name_chaletRequired = itemView.findViewById(R.id.txt_chaletRequired_reservation);
            txt_bookingDate = itemView.findViewById(R.id.txt_bookingDate_reservation);
            txt_bookingPeriod = itemView.findViewById(R.id.txt_bookingPeriod_reservation);
            txt_status = itemView.findViewById(R.id.txt_status_reservation);


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
