package com.example.sweetapp.ui.statusOfMyOrders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sweetapp.Adapter.AdapterChaletlistTenant;
import com.example.sweetapp.Adapter.AdapterRervation;
import com.example.sweetapp.DetailsTenantActivity;
import com.example.sweetapp.FilterActivity;
import com.example.sweetapp.R;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class statusOfMyOrdersFragment extends Fragment {
    private static View root;
    RecyclerView rec_status;
    DatabaseReference statusRef;
    ArrayList<Request> models;
    ValueEventListener valueEventListener;
    Request modelRequest;
    AdapterRervation adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root != null) {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null)
                parent.removeView(root);
        }
        try {
            root = inflater.inflate(R.layout.fragment_status_of_my_orders, container, false);

        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

//        listOfChaletsViewModel = new ViewModelProvider(this).get(ListOfChaletsViewModel.class);
//        textView = root.findViewById(R.id.text_home);
        rec_status = root.findViewById(R.id.rec_status);
        statusRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Order_Reservation");

        models = new ArrayList<>();
        rec_status.setLayoutManager(new LinearLayoutManager(getContext()));


        valueEventListener = statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(getContext(),dataSnapshot.getValue()+"", Toast.LENGTH_SHORT).show();
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    modelRequest = snapshot.getValue(Request.class);
//                    Log.d("TT", modelChalet.getPrice() + "");
//                    Log.d("TT", modelChalet.getAddress() + "");
//                    Log.d("TT", modelChalet.getImage() + "");

                    models.add(modelRequest);

                }
                adapter = new AdapterRervation(models, getContext());
                rec_status.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }
}