package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TenantLoginActivity extends AppCompatActivity {
    private TextView TenantForgetPass;
    private EditText TenantLoginPassword,TenantLoginEmail;
    private Button TenantLoginbtn;
    private FirebaseAuth mAuth;
    private ImageView back;
    String Email,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_login);
        TenantLoginPassword  =findViewById(R.id.TenantLoginPassword);
        TenantLoginEmail  =findViewById(R.id.TenantLoginEmail);
        TenantLoginbtn  =findViewById(R.id.TenantLoginbtn);
        TenantForgetPass  =findViewById(R.id.TenantForgetPass);
        back=findViewById(R.id.back_Tenant_Login);

        mAuth = FirebaseAuth.getInstance();
        TenantForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantLoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(TenantLoginActivity.this,TenantSingUpOrLoginActivity.class);
        startActivity(intent);
        finish();
    }
});
        TenantLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singin();
            }
        });
    }


    private void singin() {
        Email= TenantLoginEmail.getText().toString().trim();
        Password= TenantLoginPassword.getText().toString().trim();
        if (Email.isEmpty()) {
            TenantLoginEmail.setError("Email is required");
            TenantLoginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            TenantLoginEmail.setError("Please enter a valid email");
            TenantLoginEmail.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            TenantLoginPassword.setError("Password is required");
            TenantLoginPassword.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            TenantLoginPassword.setError("Minimum lenght of password should be 6");
            TenantLoginPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(TenantLoginActivity.this, TenantMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
//            startActivity(new Intent(this, TenantMainActivity.class));
        }
    }


}