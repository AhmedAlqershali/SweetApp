package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sweetapp.Adapter.AdapterChaletlistTenant;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowFilterActivity extends AppCompatActivity {
    DatabaseReference ChaletsRef;
    ValueEventListener valueEventListener;
    ArrayList<ChaletListIteamModel> models;
    ChaletListIteamModel modelChalet;
    AdapterChaletlistTenant adapter;
    RecyclerView rec_chalitlist_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_filter);

        rec_chalitlist_filter = findViewById(R.id.rec_chalitlist_filter);
        rec_chalitlist_filter.setLayoutManager(new LinearLayoutManager(this));

        ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Chalet");
        models = new ArrayList<>();
        valueEventListener = ChaletsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    modelChalet = snapshot.getValue(ChaletListIteamModel.class);


                    models.add(modelChalet);

                }
                adapter = new AdapterChaletlistTenant(models, ShowFilterActivity.this);
                rec_chalitlist_filter.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String address = getIntent().getExtras().getString("address");
        int min = getIntent().getExtras().getInt("min");
        int max = getIntent().getExtras().getInt("max");
        Float myRating = getIntent().getExtras().getFloat("myRating");

        firebaseUserSearch(address,min,max,myRating);

    }
    private void firebaseUserSearch(String address,int min, int max ,Float myRating) {

//        Toast.makeText(this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQueryAddress = ChaletsRef.orderByChild("address").startAt(address).endAt(address + "\uf8ff");
        firebaseSearchQueryAddress.addListenerForSingleValueEvent(valueEventListener);
        Query firebaseSearchQueryPrice = ChaletsRef.orderByChild("price").startAt(min).endAt(max);
        firebaseSearchQueryPrice.addListenerForSingleValueEvent(valueEventListener);
        Query firebaseSearchQueryEvaluation_Chalet = ChaletsRef.orderByChild("evaluation_Chalet").startAt(myRating).endAt(myRating);
        firebaseSearchQueryEvaluation_Chalet.addListenerForSingleValueEvent(valueEventListener);

        adapter = new AdapterChaletlistTenant(models,this);
        rec_chalitlist_filter.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}