package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sweetapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TenantSingUpActivity extends AppCompatActivity {

    private EditText TenantName,TenantPhone,TenantEmail,TenantPassword;
    private Button TenantRegister;
    FirebaseAuth mAuth;
    String Email, Password,Uid,Name,PhoneNumber;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_sing_up);

        TenantName =findViewById(R.id.TenantName);
        TenantPhone =findViewById(R.id.TenantPhone);
        TenantEmail =findViewById(R.id.TenantEmail);
        TenantPassword =findViewById(R.id.TenantPassword);
        TenantRegister =findViewById(R.id.TenantRegister);
//        back=findViewById(R.id.back_Chalet_Owner_SgintUp);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        rootRef=database.getReference("Sweet App");
        TenantRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerTenant();
            }
        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(TenantSingUpActivity.this,TenantSingUpOrLoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }

    private void registerTenant() {
        Email = TenantEmail.getText().toString().trim();
        Password = TenantPassword.getText().toString().trim();
        Name = TenantName.getText().toString().trim();
        PhoneNumber = TenantPhone.getText().toString().trim();
        if (Email.isEmpty()) {
            TenantEmail.setError("Please Enter Email");
            TenantEmail.requestFocus();
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Password.isEmpty()) {
            TenantPassword.setError("Please Enter Password");
            TenantPassword.requestFocus();
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Password.length() < 6) {
            TenantPassword.setError("Minimum length of password should be 6");
            TenantPassword.requestFocus();
            Toast.makeText(this, "Minimum length of password should be 6", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Uid = mAuth.getCurrentUser().getUid();
                    finish();
                    HashMap tenantDetails =new HashMap();
                    tenantDetails.put("Uid",Uid);
                    tenantDetails.put("Name",Name);
                    tenantDetails.put("PhoneNumber",PhoneNumber);
                    tenantDetails.put("Email",Email);
                    tenantDetails.put("Password",Password);

                    startActivity(new Intent(TenantSingUpActivity.this, TenantMainActivity.class));
                    rootRef.child("Users").child("Tenant").child(Uid).setValue(tenantDetails);
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
    }
}