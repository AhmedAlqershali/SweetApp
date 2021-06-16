package com.example.sweetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.util.ArrayList;
import java.util.Calendar;

public class FilterActivity extends AppCompatActivity {
    ImageView colse_filter;
    Button btn_show_filter;
    CrystalRangeSeekbar crystalRangeSeekbar;
    TextView rangeTextMax,rangeTextMin , mDisplayDate;
    RatingBar ratingBar_filter;
    Spinner spinner_address;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String address;
    Number min;
    Number max;
    float myRating;
    ArrayList arrayList_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        rangeTextMax = findViewById(R.id.rangeTextMax);
        rangeTextMin = findViewById(R.id.rangeTextMin);
        crystalRangeSeekbar = findViewById(R.id.rangeSeekbar3);
        ratingBar_filter = findViewById(R.id.ratingBar_filter);
        mDisplayDate = findViewById(R.id.txt_date);

        spinner_address = findViewById(R.id.spinner_address);

        colse_filter = findViewById(R.id.colse_filter);
        btn_show_filter = findViewById(R.id.btn_show_filter);


        colse_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        arrayList_address =  new ArrayList();
        arrayList_address.add("المحافظة");
        arrayList_address.add("غزة");
        arrayList_address.add("السودانية");
        arrayList_address.add("الوسطى");
        arrayList_address.add("جنوب غزة");
        arrayList_address.add("الواحة");
        arrayList_address.add("الزهراء");
        arrayList_address.add("النصيرات");
        arrayList_address.add("الزوايدة");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList_address);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_address.setAdapter(arrayAdapter);
        spinner_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        crystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                rangeTextMin.setText("يبدأ السعر من:"+minValue);
                rangeTextMax.setText("أقصى سعر:"+maxValue);
                min = minValue;
                max = maxValue;
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
                        FilterActivity.this,
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

        ratingBar_filter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 myRating =ratingBar.getRating();
                ratingBar_filter.setRating(myRating);
                int noofstars = ratingBar_filter.getNumStars();
                Toast.makeText(FilterActivity.this, "Rating: "+myRating+"/"+noofstars, Toast.LENGTH_SHORT).show();

            }
        });

        btn_show_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this,ShowFilterActivity.class);
                intent.putExtra("address",address);
                intent.putExtra("min",Integer.parseInt(min+""));
                intent.putExtra("max",Integer.parseInt(max+""));
                intent.putExtra("myRating",myRating);
                startActivity(intent);
                finish();
            }
        });
    }
}