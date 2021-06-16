package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.UserOwnerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class ReservationOfChaletActivity extends AppCompatActivity {
    String chaletIdReservation;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView mDisplayDate,txt_name_reservation,txt_price_reservation,txt_user_name;
    Spinner spinner_time;
    String tutorialsName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_of_chalet);

        chaletIdReservation = getIntent().getExtras().getString("chaletIdReservation");
        getProductsDetails(chaletIdReservation);
        getName();
        mDisplayDate = findViewById(R.id.tvDateCalendar);
        txt_name_reservation = findViewById(R.id.txt_name_reservation);
        txt_price_reservation = findViewById(R.id.txt_price_reservation);
        spinner_time = findViewById(R.id.spinner_time);
        txt_user_name = findViewById(R.id.txt_user_name);

        ArrayList arrayList =  new ArrayList();
        arrayList.add("الفترة الصباحية");
        arrayList.add("الفترة المسائية");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_time.setAdapter(arrayAdapter);
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 tutorialsName = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,
//                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReservationOfChaletActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });



        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("a", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }
    private void getProductsDetails(String chaletId) {

        DatabaseReference ProductsRef = FirebaseDatabase.getInstance().getReference("Sweet App");
        ProductsRef.child("Chalet").child(chaletId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ChaletListIteamModel chaletListIteamModel = snapshot.getValue(ChaletListIteamModel.class);
                    txt_name_reservation.setText(chaletListIteamModel.getName_Chalet());
                    txt_price_reservation.setText(chaletListIteamModel.getPrice()+"");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

   void getName(){

        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("Sweet App");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String Uid= firebaseAuth.getCurrentUser().getUid();
        Ref.child("Users").child("Tenant").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserOwnerModel chaletListIteamModel = snapshot.getValue(UserOwnerModel.class);
                    txt_user_name.setText(chaletListIteamModel.getName());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}