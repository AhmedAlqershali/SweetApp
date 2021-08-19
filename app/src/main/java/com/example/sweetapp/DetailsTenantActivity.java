package com.example.sweetapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.Rating;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DetailsTenantActivity extends AppCompatActivity implements RatingDialogListener {

    ImageView iv_details;
    TextView txt_name_details, txt_address_details, txt_numberisPhone_details,
            txt_salary_details, txt_numOfHours_details;
    String chaletId;
    Button reservation,add_rating;
    ArrayList arrayList;
    private DatabaseReference ChaletsRefRating ;
    FirebaseAuth mAuth ;
    RatingBar rating_all ;
    float evaluation;
    String phone;
    float average;
    int count=0,sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tenant);

        iv_details = findViewById(R.id.image_detalis);
        txt_name_details = findViewById(R.id.name_details);
        txt_address_details = findViewById(R.id.address_details);
        txt_numberisPhone_details = findViewById(R.id.txt_numberisPhone_details);
        txt_salary_details = findViewById(R.id.salary_details);
        txt_numOfHours_details = findViewById(R.id.txt_numOfHours_details);
        reservation = findViewById(R.id.reservation_btn);
        add_rating = findViewById(R.id.add_rating);

        arrayList = new ArrayList();
        rating_all =  findViewById(R.id.rating_all);
        mAuth = FirebaseAuth.getInstance();
//        Intent intent = getIntent();
//        chaletId = intent.getStringExtra("chaletId");
        chaletId = getIntent().getExtras().getString("chaletTenantId");

        if (chaletId != null)
            getProductsDetails(chaletId);
        ChaletsRefRating = FirebaseDatabase.getInstance().getReference("Sweet App").child("Rating");

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsTenantActivity.this,ReservationOfChaletActivity.class);
                intent.putExtra("chaletIdReservation",chaletId);
                startActivity(intent);
            }
        });

        add_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
                getRatingChalet(chaletId);

            }
        });


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
                    rating_all.setRating(chaletListIteamModel.getEvaluation_Chalet());
                    Picasso.get().load(chaletListIteamModel.getImage()).into(iv_details);
                     phone = chaletListIteamModel.getPhone();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
//
//    public void ShowDialog()
//    {
//        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
//
//        LinearLayout linearLayout = new LinearLayout(this);
//        final RatingBar rating = new RatingBar(this);
//
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        rating.setLayoutParams(lp);
//        rating.setNumStars(5);
//        rating.setStepSize(1);
//
//        //add ratingBar to linearLayout
//        linearLayout.addView(rating);
//
//
//        popDialog.setIcon(android.R.drawable.btn_star_big_on);
//        popDialog.setTitle("إضافة تقييم:");
//
//        //add linearLayout to dailog
//        popDialog.setView(linearLayout);
//
//
//
//        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                System.out.println("Rated val:"+v);
//            }
//        });
//
//
//
//        // Button OK
//        popDialog.setPositiveButton(android.R.string.ok,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        arrayList.add(rating.getProgress());
//                        addRating(rating.getProgress());
//                        updateRating();
//                        dialog.dismiss();
//                    }
//
//                })
//
//                // Button Cancel
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        popDialog.create();
//        popDialog.show();
//
//    }

    private void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(1)
                .setTitle("Rate this Chalet")
                .setDescription("Please select some stars and give your feedback")
                .setTitleTextColor(R.color.purple_500)
                .setDescriptionTextColor(R.color.purple_500)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(R.color.white)
                .setCommentBackgroundColor(R.color.purple_700)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .create(DetailsTenantActivity.this)
                .show();
    }

    private void getRatingChalet(String chaletId) {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        Query chaletRating = ChaletsRefRating.child(uid).orderByChild("chaletId").equalTo(chaletId);
         chaletRating.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot postDataSnapshot:snapshot.getChildren()){
                     Rating item = postDataSnapshot.getValue(Rating.class);
                     sum +=Integer.parseInt(item.getRatingValue());
                     count++;
                 }
                 if (count != 0){
                  average = sum/count;
                 rating_all.setRating(average);
                     DatabaseReference ChaletsRef = FirebaseDatabase.getInstance().getReference("Sweet App");

                     Log.d("rating",average+"");

                     ChaletsRef.child("Chalet").child(chaletId).addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             if (snapshot.child("Chalet").child(chaletId).exists()){
                                 ChaletsRef.child("Chalet").child(chaletId).child("evaluation_Chalet").removeValue();
                                 ChaletsRef.child("Chalet").child(chaletId).child("evaluation_Chalet").setValue(average);
                                 Log.d("rating",average+"1");
                             }else {
                                 ChaletsRef.child("Chalet").child(chaletId).child("evaluation_Chalet").setValue(average);
                                 Log.d("rating",average+"");
                             }

                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });
             }




             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

    }

//    private void addRating(float rating) {
//        FirebaseUser user = mAuth.getCurrentUser();
//        String uid = user.getUid();
//        HashMap<String, Object> ChaletMap = new HashMap<>();
//        ChaletMap.put("rating", rating);
//        ChaletMap.put("chaletId", chaletId);
//        ChaletMap.put("uid", uid);
//
//
//
////
//
//
////        list.setChaletId(chaletId);
//         ChaletsRefRating = FirebaseDatabase.getInstance().getReference("Sweet App");
//        ChaletsRefRating.child("Rating").child(uid).child(chaletId).updateChildren(ChaletMap)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//
//                            Toast.makeText(DetailsTenantActivity.this, "شكرا على تقييمك لنا", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            String message = task.getException().toString();
//                            Toast.makeText(DetailsTenantActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//    private void updateRating()
//    {
//        FirebaseUser user = mAuth.getCurrentUser();
//        String uid = user.getUid();
//
//
//        DatabaseReference x = FirebaseDatabase.getInstance().getReference("Sweet App");
//        ChaletsRefRating.child("Rating").child(uid).child(chaletId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    Rating myRating = snapshot.getValue(Rating.class);
//                    for (int i=0; i <= myRating.getRating() ; i++) {
//                        evaluation += myRating.getRating()/5;
//                    }
//                    Toast.makeText(DetailsTenantActivity.this, ""+myRating.getRating(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(DetailsTenantActivity.this, ""+evaluation, Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        HashMap<String, Object> ChaletMap = new HashMap<>();
//        ChaletMap.put("evaluation_Chalet", evaluation);
//        DatabaseReference ChaletsRef = FirebaseDatabase.getInstance().getReference("Sweet App");
//
//         ChaletsRef.child("Chalet").child(chaletId).updateChildren(ChaletMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(DetailsTenantActivity.this, "تم التعديل التقييم", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }


    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {
        Rating rating = new Rating(phone,chaletId,String.valueOf(value),comments);
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        ChaletsRefRating.child(uid).child(chaletId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).child(chaletId).exists()){
                    ChaletsRefRating.child(uid).child(chaletId).removeValue();
                    ChaletsRefRating.child(uid).child(chaletId).setValue(rating);
                }else {
                    ChaletsRefRating.child(uid).child(chaletId).setValue(rating);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}