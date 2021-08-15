package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetapp.Adapter.AdapterChaletlistOwner;
import com.example.sweetapp.Adapter.AdapterOrderStatus;
import com.example.sweetapp.model.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
//
//import com.example.sweetapp.Interface.ItemClickListener;
//import com.example.sweetapp.ViewHolder.OrderViewHolder;
//import com.example.sweetapp.model.ChaletListIteamModel;
//import com.example.sweetapp.model.Common;
//import com.example.sweetapp.model.Request;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.jaredrummler.materialspinner.MaterialSpinner;
//import com.squareup.picasso.Picasso;

public class OrderStatusActivity extends AppCompatActivity {
    RecyclerView rec_orderstatus;
    Request modelChalet;

    AdapterOrderStatus adapter;
    DatabaseReference ChaletsRef;
    FirebaseAuth mAuth;

    public TextView txt_key, txt_chaletRequired, txt_phone, txt_theNameOrder, txt_bookingDate, txt_bookingPeriod, txt_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        mAuth = FirebaseAuth.getInstance();

        rec_orderstatus = findViewById(R.id.rec_orderstatus);

        String chaletId = "-Mh5eTgZkBE6XZdS83AU";
        getProductsDetails(chaletId);


    }

    private void getProductsDetails(String chaletId) {



        ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Order_Reservation");


        ArrayList<Request> models = new ArrayList<>();
        rec_orderstatus.setLayoutManager(new LinearLayoutManager(this));


        ChaletsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    modelChalet = snapshot.getValue(Request.class);


                    models.add(modelChalet);

                }
                adapter = new AdapterOrderStatus(models, OrderStatusActivity.this);
                rec_orderstatus.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new AdapterOrderStatus.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderStatusActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.update_orderstatus_layout,null);
                        mBuilder.setTitle("حالة الطلب :");
                        Spinner spinner = mView.findViewById(R.id.sp_statusSpinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderStatusActivity.this,
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.statusList));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        mBuilder.setPositiveButton("ارسال", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!spinner.getSelectedItem().toString().equalsIgnoreCase("choose"))
                                    Toast.makeText(OrderStatusActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                String selectedStatus = spinner.getSelectedItem().toString();
                                updateStatus(selectedStatus);
                                dialogInterface.dismiss();
                            }
                        });

                        mBuilder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                            }
                        });
                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void updateStatus(String selectedStatus)
    {

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Order_Reservation").child(uid);

        HashMap<String, Object> ChaletMap = new HashMap<>();
//        ChaletMap.put("Email", modelChalet.getName_chalet());
//        ChaletMap.put("Name",  modelChalet.getName_applicant());
//        ChaletMap.put("PhoneNumber",  modelChalet.getBooking_period());
        ChaletMap.put("status",  selectedStatus);

        ChaletsRef.updateChildren(ChaletMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(OrderStatusActivity.this, "تم التعديل", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}