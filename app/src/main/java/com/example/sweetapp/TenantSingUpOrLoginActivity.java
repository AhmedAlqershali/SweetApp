package com.example.sweetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sweetapp.R;

public class TenantSingUpOrLoginActivity extends AppCompatActivity {
    private Button singUpTenant_btn,loginTenant_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_sing_up_or_login);
        singUpTenant_btn = findViewById(R.id.tenantSingup_btn);
        loginTenant_btn = findViewById(R.id.tenantLogin_btn);

        singUpTenant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TenantSingUpOrLoginActivity.this, TenantSingUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginTenant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TenantSingUpOrLoginActivity.this, TenantLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}