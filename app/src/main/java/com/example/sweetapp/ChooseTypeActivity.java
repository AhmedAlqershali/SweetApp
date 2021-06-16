package com.example.sweetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChooseTypeActivity extends AppCompatActivity {

    Button btn_Chalet_Owner,btn_tenant;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        btn_Chalet_Owner=findViewById(R.id.btn_Chalet_Owner);
        btn_tenant=findViewById(R.id.btn_tenant);
        mAuth =FirebaseAuth.getInstance();


        btn_Chalet_Owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseTypeActivity.this, ChaletOwnerSingUpOrLoginActivity.class);
                startActivity(intent);
//                finish();
            }
        });


        btn_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseTypeActivity.this, TenantSingUpOrLoginActivity.class);
                startActivity(intent);
//                finish();
            }
        });

    }
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sendUserToActivety();
        }
    }

    private void sendUserToActivety() {
        Intent regIntent = new Intent(ChooseTypeActivity.this, MainActivity.class);
        startActivity(regIntent);
    }
}