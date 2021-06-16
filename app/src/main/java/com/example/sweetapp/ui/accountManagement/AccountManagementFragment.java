package com.example.sweetapp.ui.accountManagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sweetapp.EditChaletActivity;
import com.example.sweetapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class AccountManagementFragment extends Fragment {
     TextView textView;
    private AccountManagementViewModel accountManagementViewModel;
    DatabaseReference ChaletsRef;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String  downloadImageUrl;
    private StorageReference ProductImagesRef;
    String Uid ,nameChalet;
    StorageReference filePath;
    EditText TenantName,TenantPhone,TenantEmail;
    String name,phone,email;
    ImageView image_tenant;
    Button TenantRegister;
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountManagementViewModel =
                new ViewModelProvider(this).get(AccountManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_accountmanagement, container, false);
        mAuth = FirebaseAuth.getInstance();
         Uid=mAuth.getUid();

        ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Users").child("Tenant").child(Uid);

        TenantName = root.findViewById(R.id.ed_TenantName);
        TenantPhone = root.findViewById(R.id.ed_TenantPhone);
        TenantEmail = root.findViewById(R.id.ed_TenantEmail);
        image_tenant = root.findViewById(R.id.image_tenant);
        TenantRegister = root.findViewById(R.id.edit_Tenant);
        Toast.makeText(getContext(), "sss"+Uid, Toast.LENGTH_SHORT).show();
        Log.d("e",mAuth.getUid());

        TenantRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StoreProductInformation();
                SaveProductInfoToDatabase();



            }
        });

        return root;
    }


    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            image_tenant.setImageURI(ImageUri);
        }
    }



//
//    private void StoreProductInformation()
//    {
//
//          filePath = ProductImagesRef.child(ImageUri.getLastPathSegment()  + ".jpg");
//
//        final UploadTask uploadTask = filePath.putFile(ImageUri);
//
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e)
//            {
//                String message = e.toString();
//                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//            {
//                Toast.makeText(getContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
//
//                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
//                    {
//                        if (!task.isSuccessful())
//                        {
//                            throw task.getException();
//                        }
//
//                        downloadImageUrl = filePath.getDownloadUrl().toString();
//                        return filePath.getDownloadUrl();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task)
//                    {
//                        if (task.isSuccessful())
//                        {
//                            downloadImageUrl = task.getResult().toString();
//
//                            Toast.makeText(getContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
//
//                            SaveProductInfoToDatabase();
//                        }
//                    }
//                });
//            }
//        });
//    }



    private void SaveProductInfoToDatabase()
    {
        email = TenantEmail.getText().toString();
        name = TenantName.getText().toString();
        phone = TenantPhone.getText().toString();


        HashMap<String, Object> ChaletMap = new HashMap<>();
        ChaletMap.put("Email", email);
        ChaletMap.put("Name", name);
        ChaletMap.put("PhoneNumber", phone);

        ChaletsRef.updateChildren(ChaletMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "تم التعديل", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}