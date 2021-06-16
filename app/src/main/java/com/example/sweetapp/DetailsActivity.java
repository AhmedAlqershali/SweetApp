package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sweetapp.model.ChaletListIteamModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView iv_details;
    TextView txt_name_details, txt_address_details, txt_numberisPhone_details,
            txt_salary_details, txt_numOfHours_details;
    String chaletId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        iv_details = findViewById(R.id.image_detalis);
        txt_name_details = findViewById(R.id.name_details);
        txt_name_details = findViewById(R.id.name_details);
        txt_address_details = findViewById(R.id.address_details);
        txt_numberisPhone_details = findViewById(R.id.txt_numberisPhone_details);
        txt_salary_details = findViewById(R.id.salary_details);
        txt_numOfHours_details = findViewById(R.id.txt_numOfHours_details);


//        Intent intent = getIntent();
//        chaletId = intent.getStringExtra("chaletId");
        chaletId = getIntent().getExtras().getString("chaletId");

        getProductsDetails(chaletId);


    }

    private void getProductsDetails(String chaletId) {

        DatabaseReference ProductsRef = FirebaseDatabase.getInstance().getReference("Sweet App");
        ProductsRef.child("Chalet").child(chaletId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ChaletListIteamModel chaletListIteamModel = snapshot.getValue(ChaletListIteamModel.class);
                    txt_name_details.setText(chaletListIteamModel.getName_Chalet());
                    txt_salary_details.setText(chaletListIteamModel.getPrice()+"");
                    txt_address_details.setText(chaletListIteamModel.getAddress());
                    txt_numberisPhone_details.setText(chaletListIteamModel.getPhone());
                    txt_numOfHours_details.setText(chaletListIteamModel.getNum_Of_Hours());
                    Picasso.get().load(chaletListIteamModel.getImage()).into(iv_details);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}