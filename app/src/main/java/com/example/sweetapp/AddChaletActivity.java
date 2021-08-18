package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.ServiceModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manojbhadane.QButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddChaletActivity extends AppCompatActivity {

    Button Services;
    private String saveCurrentDate, saveCurrentTime, NameChalet, Address, numberisPhone, salary, numOfHours,chaletId,uid, Services1, Services2, Services3, Services4, Services5, Services6, Services7, Services8, Services9;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String chaletRandomKey, downloadImageUrl;
    private StorageReference ChaletImagesRef;
    private DatabaseReference ChaletsRef;
    private ProgressDialog loadingBar;
    FirebaseAuth mAuth;
    ChaletListIteamModel list;

    ImageView imgChalet;
    QButton txtServices1, txtServices2, txtServices3, txtServices4, txtServices5, txtServices6,
            txtServices7, txtServices8, txtServices9;
    EditText edNameChalet, edaddress, edsalary, ednumberisPhone, ednumOfHours;
    Button btn_add;


    boolean SwimmingPoolMen = false, SwimmingPoolSmall = false, Stadium = false, Wifi = false, Billiard = false,
            Kitchen = false, Garage = false, Stereo = false, TennisTable = false ;


    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chalet);



        mAuth = FirebaseAuth.getInstance();

        ChaletImagesRef = FirebaseStorage.getInstance().getReference().child("Chalet Images");
        ChaletsRef = FirebaseDatabase.getInstance().getReference("Sweet App");

        Services = findViewById(R.id.Services);
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        chaletId  =  FirebaseDatabase.getInstance().getReference("Users").child("Chalet Owner").child(uid).push().getKey();


        btn_add = (Button) findViewById(R.id.btn_addchalet);
        imgChalet = (ImageView) findViewById(R.id.Img_chalet);
        edNameChalet = (EditText) findViewById(R.id.ed_nameChalet);
        edaddress = (EditText) findViewById(R.id.ed_nameaddress);
        edsalary = (EditText) findViewById(R.id.ed_salarychalet);
        ednumberisPhone = (EditText) findViewById(R.id.ed_numberisPhone);
        ednumOfHours = (EditText) findViewById(R.id.ed_numOfHours);

        loadingBar = new ProgressDialog(this);


        txtServices1 =  findViewById(R.id.txt_Services1);
        txtServices2 =  findViewById(R.id.txt_Services2);
        txtServices3 =  findViewById(R.id.txt_Services3);
        txtServices4 = findViewById(R.id.txt_Services4);
        txtServices5 =  findViewById(R.id.txt_Services5);
        txtServices6 =  findViewById(R.id.txt_Services6);
        txtServices7 =  findViewById(R.id.txt_Services7);
        txtServices8 =  findViewById(R.id.txt_Services8);
        txtServices9 =  findViewById(R.id.txt_Services9);


        Services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPopUpWindow();
            }
        });

        imgChalet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });



    }
    private void openPopUpWindow() {

        Intent PopUpWindow = new Intent(AddChaletActivity.this, PopUpWindowChalet.class);
        startActivity(PopUpWindow);


        if (getIntent()!=null){
            SwimmingPoolMen = getIntent().getExtras().getBoolean("checkbox_swimming_pool_men");
            SwimmingPoolSmall = getIntent().getExtras().getBoolean("checkbox_swimming_pool_small");
            Stadium = getIntent().getExtras().getBoolean("checkbox_stadium");
            Wifi = getIntent().getExtras().getBoolean("checkbox_Wifi");
            Billiard = getIntent().getExtras().getBoolean("checkbox_billiard");
            Kitchen = getIntent().getExtras().getBoolean("checkbox_kitchen");
            Garage = getIntent().getExtras().getBoolean("checkbox_garage");
            Stereo = getIntent().getExtras().getBoolean("checkbox_stereo");
            TennisTable = getIntent().getExtras().getBoolean("checkbox_tennis_table");
        }

        if (SwimmingPoolMen==true)
            txtServices1.setBackgroundColor(Color.GREEN);
        if (SwimmingPoolSmall==true)
            txtServices2.setBackgroundColor(Color.GREEN);
        if (Stadium==true)
            txtServices3.setBackgroundColor(Color.GREEN);
        if (Wifi==true)
            txtServices3.setBackgroundColor(Color.GREEN);
        if (Billiard==true)
            txtServices4.setBackgroundColor(Color.GREEN);
        if (Kitchen==true)
            txtServices5.setBackgroundColor(Color.GREEN);
        if (Garage==true)
            txtServices6.setBackgroundColor(Color.GREEN);
        if (Stereo==true)
            txtServices7.setBackgroundColor(Color.GREEN);
        if (TennisTable==true)
            txtServices8.setBackgroundColor(Color.GREEN);


        arrayList.add(new ServiceModel(SwimmingPoolMen ,SwimmingPoolSmall,Stadium,Wifi,Billiard,Kitchen,Garage,Stereo,TennisTable));
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            imgChalet.setImageURI(ImageUri);
        }
    }


    private void ValidateProductData() {
        NameChalet = edNameChalet.getText().toString();
        Address = edaddress.getText().toString();
        salary = edsalary.getText().toString();
        numberisPhone = ednumberisPhone.getText().toString();
        numOfHours = ednumOfHours.getText().toString();
        Services1 = txtServices1.getText().toString();
        Services2 = txtServices1.getText().toString();
        Services3 = txtServices3.getText().toString();
        Services4 = txtServices4.getText().toString();
        Services5 = txtServices5.getText().toString();
        Services6 = txtServices6.getText().toString();
        Services7 = txtServices7.getText().toString();
        Services8 = txtServices8.getText().toString();
        Services9 = txtServices9.getText().toString();


        if (ImageUri == null) {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(NameChalet)) {
            Toast.makeText(this, "Please write Name Chalet...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Address)) {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(salary)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(numberisPhone)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(numOfHours)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        } else {
            StoreProductInformation();
        }
    }


    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new Chalet.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        chaletRandomKey = saveCurrentDate + " " + saveCurrentTime;


        final StorageReference filePath = ChaletImagesRef.child(ImageUri.getLastPathSegment() + chaletRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddChaletActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddChaletActivity.this, "Chalet Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddChaletActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }


    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> ChaletMap = new HashMap<>();
        ChaletMap.put("chaletId", chaletId);
        ChaletMap.put("image", downloadImageUrl);
        ChaletMap.put("name_Chalet", NameChalet);
        ChaletMap.put("price", Integer.parseInt(salary));
        ChaletMap.put("address", Address);
        ChaletMap.put("evaluation_Chalet", 0f);
        ChaletMap.put("phone", numberisPhone);
        ChaletMap.put("num_Of_Hours", numOfHours);
        ChaletMap.put("chaletOwnerId", uid);
        ChaletMap.put("date", saveCurrentDate);
        ChaletMap.put("time", saveCurrentTime);
        ChaletMap.put("services", arrayList);


        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();


//        list.setChaletId(chaletId);
        ChaletsRef.child("Chalet").child(chaletId).updateChildren(ChaletMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent idToList = new Intent(AddChaletActivity.this, MainActivity.class);
                            startActivity(idToList);
//                            Intent idToLModel = new Intent(AddChaletActivity.this, AdapterChaletlistOwner.class);
//                            idToList.putExtra("chaletId",chaletId);
//                            idToLModel.putExtra("chaletId",chaletId);

//                            startActivity(idToLModel);

                            loadingBar.dismiss();
                            Toast.makeText(AddChaletActivity.this, "Chalet is added successfully..", Toast.LENGTH_SHORT).show();

                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddChaletActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}