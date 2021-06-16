package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sweetapp.Interface.ItemClickListener;
import com.example.sweetapp.ViewHolder.OrderViewHolder;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.Common;
import com.example.sweetapp.model.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

public class OrderStatusActivity extends AppCompatActivity {
    RecyclerView rec_orderstatus;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    DatabaseReference requests;
    MaterialSpinner spinner;
    public TextView txt_key,txt_chaletRequired,txt_phone,txt_theNameOrder,txt_bookingDate,txt_bookingPeriod,txt_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


//        db = FirebaseDatabase.getInstance();
        requests = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Requests");

        rec_orderstatus = findViewById(R.id.rec_orderstatus);
        rec_orderstatus.setHasFixedSize(true);
        rec_orderstatus.setLayoutManager(new LinearLayoutManager(this));
        
//        loadOrders();

    }



    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.orderstatus_layout,
                OrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Request request, int i) {
                orderViewHolder.txt_key.setText(adapter.getRef(i).getKey());
                orderViewHolder.txt_theNameOrder.setText(request.getTheNameOrder());
                orderViewHolder.txt_phone.setText(request.getPhone());
                orderViewHolder.txt_status.setText(Common.convertCodeToStatus(request.getStatus()));
                orderViewHolder.txt_chaletRequired.setText(request.getChaletRequired());
                orderViewHolder.txt_bookingDate.setText(request.getBookingDate());
                orderViewHolder.txt_bookingPeriod.setText(request.getBookingPeriod());

                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });

//                requests.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            Request myRequest = snapshot.getValue(Request.class);
////                            orderViewHolder.txt_key.setText(adapter.getRef(i).getKey());
//                            orderViewHolder.txt_theNameOrder.setText(myRequest.getTheNameOrder());
//                            orderViewHolder.txt_status.setText(Common.convertCodeToStatus(myRequest.getStatus()));
//                            orderViewHolder.txt_chaletRequired.setText(myRequest.getChaletRequired());
//                            orderViewHolder.txt_bookingDate.setText(myRequest.getBookingDate());
//                            orderViewHolder.txt_bookingPeriod.setText(myRequest.getBookingPeriod());
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            }
        };
        adapter.notifyDataSetChanged();
        rec_orderstatus.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("تعديل"))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else if (item.getTitle().equals("حذف"))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
            return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
    }

    private void showUpdateDialog(String key, Request item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatusActivity.this);
        alertDialog.setTitle("تعديل الطلب");
        alertDialog.setMessage("اختر:");

        LayoutInflater inflater = this.getLayoutInflater();
        final  View view = inflater.inflate(R.layout.update_order_layout,null);
        spinner = view.findViewById(R.id.statusSpinner);
        spinner.setItems( "الشالية غير متاح حاليا","قبول الطلب", "رفض الطلب");
        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("ارسال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);
            }
        });

        alertDialog.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();


    }
}