package com.example.sweetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChaletOwnerSingUpOrLoginActivity extends AppCompatActivity {
    private Button sinupChalet_btn,loginChaleat_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalet_owner_sing_up_or_login);
        sinupChalet_btn = findViewById(R.id.chalatOwnerSingup_btn);
        loginChaleat_btn = findViewById(R.id.chalatOwnerLogin_btn);

        sinupChalet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChaletOwnerSingUpOrLoginActivity.this, ChaletOwnerSingupActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        loginChaleat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChaletOwnerSingUpOrLoginActivity.this, ChalatOwnetLoginActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }
}