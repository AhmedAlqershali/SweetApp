package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ChalatOwnetLoginActivity extends AppCompatActivity {
    private TextView ChaletOwnerForgetPass;
    private EditText ChaletOwnerLoginPassword,ChaletOwnerLoginEmail;
    private Button ChaletOwnerLoginbtn;
    private FirebaseAuth mAuth;
    String Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalat_ownet_login);

        ChaletOwnerLoginPassword  =findViewById(R.id.ChaletOwnerLoginPassword);
        ChaletOwnerLoginEmail  =findViewById(R.id.ChaletOwnerLoginEmail);
        ChaletOwnerLoginbtn  =findViewById(R.id.ChaletOwnerLoginbtn);
        ChaletOwnerForgetPass  =findViewById(R.id.ChaletOwnerForgetPass);

        mAuth = FirebaseAuth.getInstance();
        ChaletOwnerForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ChalatOwnetLoginActivity.this,ForgetPasswordActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        ChaletOwnerLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singin();
            }
        });
    }

    private void singin() {
        Email= ChaletOwnerLoginEmail.getText().toString().trim();
        Password= ChaletOwnerLoginPassword.getText().toString().trim();
        if (Email.isEmpty()) {
            ChaletOwnerLoginEmail.setError("Email is required");
            ChaletOwnerLoginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            ChaletOwnerLoginEmail.setError("Please enter a valid email");
            ChaletOwnerLoginEmail.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            ChaletOwnerLoginPassword.setError("Password is required");
            ChaletOwnerLoginPassword.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            ChaletOwnerLoginPassword.setError("Minimum lenght of password should be 6");
            ChaletOwnerLoginPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(ChalatOwnetLoginActivity.this, MainActivity.class);
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
            startActivity(new Intent(this, MainActivity.class));
        }
    }


}