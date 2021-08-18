package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sweetapp.R;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditChaletActivity extends AppCompatActivity {
    ImageView iv_Edit;

    EditText txt_name_Edit, txt_address_Edit, txt_numberisPhone_Edit,
            txt_salary_Edit, txt_numOfHours_Edit;
    Button btn_edit;
    DatabaseReference ChaletsRef;
    String name_Chalet, address, phone, numOfHour, price, image;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String  downloadImageUrl;
    private StorageReference ProductImagesRef;
    String chaletId ,nameChalet;
     StorageReference filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chaletId = getIntent().getExtras().getString("ChaletId");
        nameChalet = getIntent().getExtras().getString("nameChalet");
        ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Chalet").child(chaletId);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Chalet Images");

        setContentView(R.layout.activity_edit_chalet);
        txt_name_Edit = findViewById(R.id.ed_nameChalet_edit);
        txt_address_Edit = findViewById(R.id.ed_nameaddress_edit);
        txt_numberisPhone_Edit = findViewById(R.id.ed_numberisPhone_edit);
        txt_salary_Edit = findViewById(R.id.ed_salarychalet_edit);
        txt_numOfHours_Edit = findViewById(R.id.ed_numOfHours_edit);
        iv_Edit = findViewById(R.id.Img_chalet_edit);
        btn_edit = findViewById(R.id.btn_edit);



        ChaletsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ChaletListIteamModel chaletListIteamModel = snapshot.getValue(ChaletListIteamModel.class);
                    txt_name_Edit.setText(chaletListIteamModel.getName_Chalet());
                    txt_salary_Edit.setText(chaletListIteamModel.getPrice()+"");
                    txt_address_Edit.setText(chaletListIteamModel.getAddress());
                    txt_numberisPhone_Edit.setText(chaletListIteamModel.getPhone());
                    txt_numOfHours_Edit.setText(chaletListIteamModel.getNum_Of_Hours());
                    Picasso.get().load(chaletListIteamModel.getImage()).into(iv_Edit);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreProductInformation();
                SaveProductInfoToDatabase();

                finish();
            }

        });

        iv_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


    }




    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            iv_Edit.setImageURI(ImageUri);
        }
    }


    private void StoreProductInformation()
    {

          filePath = ProductImagesRef.child(ImageUri.getLastPathSegment()  + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(EditChaletActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(EditChaletActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(EditChaletActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }



    private void SaveProductInfoToDatabase()
    {


        name_Chalet = txt_name_Edit.getText().toString();
        address = txt_address_Edit.getText().toString();
        phone = txt_numberisPhone_Edit.getText().toString();
        numOfHour = txt_numOfHours_Edit.getText().toString();
        price = txt_salary_Edit.getText().toString();

        int priceChalet = Integer.parseInt(price);

        HashMap<String, Object> ChaletMap = new HashMap<>();
        ChaletMap.put("name_Chalet", name_Chalet);
        ChaletMap.put("image", downloadImageUrl);
        ChaletMap.put("address", address);
        ChaletMap.put("price", priceChalet);
        ChaletMap.put("phone", phone);
        ChaletMap.put("num_Of_Hours", numOfHour);

        ChaletsRef.updateChildren(ChaletMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditChaletActivity.this, "تم التعديل", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}