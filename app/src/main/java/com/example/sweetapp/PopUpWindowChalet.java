package com.example.sweetapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.sweetapp.R;

public class PopUpWindowChalet extends AppCompatActivity {
    CheckBox checkbox_swimming_pool_men,checkbox_swimming_pool_small,checkbox_stadium,checkbox_Wifi,checkbox_billiard,
            checkbox_kitchen, checkbox_garage,checkbox_stereo,checkbox_tennis_table;


    boolean swimming_pool_men = false, swimming_pool_small = false, stadium = false, Wifi = false, billiard = false,
            kitchen = false, garage = false, stereo = false, tennis_table = false ;

    Button btn_checkbox;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window_chalet);

        checkbox_swimming_pool_men=findViewById(R.id.checkbox_swimming_pool_men);
        checkbox_swimming_pool_small=findViewById(R.id.checkbox_swimming_pool_Small);
        checkbox_stadium=findViewById(R.id.checkbox_stadium);
        checkbox_Wifi=findViewById(R.id.checkbox_Wifi);
        checkbox_billiard=findViewById(R.id.checkbox_billiard);
        checkbox_kitchen=findViewById(R.id.checkbox_kitchen);
        checkbox_garage=findViewById(R.id.checkbox_garage);
        checkbox_stereo=findViewById(R.id.checkbox_stereo);
        checkbox_tennis_table=findViewById(R.id.checkbox_tennis_table);

        btn_checkbox=findViewById(R.id.btn_checkbox);




        btn_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pop_checkbox();




            }
        });







        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int Width=dm.widthPixels;
        int hight=dm.heightPixels;


        getWindow().setLayout((int)(Width*.7),(int)(hight*.5));

        WindowManager.LayoutParams params =getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;


        getWindow().setAttributes(params);




    }


    private void Pop_checkbox(){

        if (checkbox_swimming_pool_men.isChecked())
            swimming_pool_men=true;
        else
            swimming_pool_men=false;


        if (checkbox_swimming_pool_small.isChecked())
            swimming_pool_small=true;
        else
            swimming_pool_small=false;


        if (checkbox_billiard.isChecked())
            billiard=true;
        else
            billiard=false;


        if (checkbox_garage.isChecked())
            garage=true;
        else
            garage=false;


        if (checkbox_kitchen.isChecked())
            kitchen=true;
        else
            kitchen=false;


        if (checkbox_stadium.isChecked())
            stadium=true;
        else
            stadium=false;


        if (checkbox_stereo.isChecked())
            stereo=true;
        else
            stereo=false;


        if (checkbox_Wifi.isChecked())
            Wifi=true;
        else
            Wifi=false;




        if (checkbox_tennis_table.isChecked())
            tennis_table=true;
        else
            tennis_table=false;


        Intent intent=new Intent(PopUpWindowChalet.this,AddChaletActivity.class);
        intent.putExtra("checkbox_swimming_pool_men",checkbox_swimming_pool_men.isChecked());
        intent.putExtra("checkbox_swimming_pool_small",checkbox_swimming_pool_small.isChecked());
        intent.putExtra("checkbox_stadium",checkbox_stadium.isChecked());
        intent.putExtra("checkbox_Wifi",checkbox_Wifi.isChecked());
        intent.putExtra("checkbox_billiard",checkbox_billiard.isChecked());
        intent.putExtra("checkbox_kitchen",checkbox_kitchen.isChecked());
        intent.putExtra("checkbox_garage",checkbox_garage.isChecked());
        intent.putExtra("checkbox_stereo",checkbox_stereo.isChecked());
        intent.putExtra("checkbox_tennis_table",checkbox_tennis_table.isChecked());
        startActivity(intent);


    }







}