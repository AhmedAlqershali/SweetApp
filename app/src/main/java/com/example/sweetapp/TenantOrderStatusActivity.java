package com.example.sweetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sweetapp.ViewHolder.OrderViewHolder;
import com.example.sweetapp.model.Common;
import com.example.sweetapp.model.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class TenantOrderStatusActivity extends AppCompatActivity {
    RecyclerView rec_orderstatus;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    DatabaseReference requests;
    MaterialSpinner spinner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_order_status);

        requests = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Requests");

        rec_orderstatus = findViewById(R.id.rec_listorder);
        rec_orderstatus.setHasFixedSize(true);
        rec_orderstatus.setLayoutManager(new LinearLayoutManager(this));
//        if (getIntent() == null)
//        loadOrders(Common.chaletListIteamModel.getPhone());
//        else
//            loadOrders(getIntent().getStringExtra("phone"));
    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.orderstatus_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                .equalTo(phone)
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
            }
        };
        rec_orderstatus.setAdapter(adapter);
    }
}