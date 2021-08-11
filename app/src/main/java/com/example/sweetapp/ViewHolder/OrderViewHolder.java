//package com.example.sweetapp.ViewHolder;
//
//import android.os.Build;
//import android.view.ContextMenu;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.sweetapp.Adapter.AdapterFavorits;
//import com.example.sweetapp.Interface.ItemClickListener;
//import com.example.sweetapp.R;
//
//public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnCreateContextMenuListener{
//    public TextView txt_key,txt_chaletRequired,txt_phone,txt_theNameOrder,txt_bookingDate,txt_bookingPeriod,txt_status;
//
//    private ItemClickListener itemClickListener;
//
//    public OrderViewHolder(View itemView){
//        super(itemView);
//
//        txt_key = itemView.findViewById(R.id.txt_key);
//        txt_chaletRequired = itemView.findViewById(R.id.txt_chaletRequired);
//        txt_theNameOrder = itemView.findViewById(R.id.txt_theNameOrder);
//        txt_bookingDate = itemView.findViewById(R.id.txt_bookingDate);
//        txt_bookingPeriod = itemView.findViewById(R.id.txt_bookingPeriod);
//        txt_status = itemView.findViewById(R.id.txt_status);
//        txt_phone = itemView.findViewById(R.id.txt_phone);
//
//        itemView.setOnClickListener(this);
//        itemView.setOnCreateContextMenuListener(this);
//
//    }
//    public void setItemClickListener(ItemClickListener itemClickListener){
//        this.itemClickListener=itemClickListener;
//    }
//
//    @Override
//    public void onClick(View view) {
//        itemClickListener.onClick(view,getAdapterPosition(),false);
//    }
//
//
//    @Override
//    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        contextMenu.setHeaderTitle("Select The Action");
//
//        contextMenu.add(0,0,getAdapterPosition(),"قبول الطلب");
//        contextMenu.add(0,1,getAdapterPosition(),"رفض الطلب");
//
//    }
//}
