package com.example.sweetapp.ui.listOfChalets;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweetapp.Adapter.AdapterChaletlistOwner;
import com.example.sweetapp.AddChaletActivity;
import com.example.sweetapp.DetailsActivity;
import com.example.sweetapp.DetailsTenantActivity;
import com.example.sweetapp.MainActivity;
import com.example.sweetapp.R;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.ui.listOfChalets.ListOfChaletsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfChaletsFragment extends Fragment {

    private ListOfChaletsViewModel listOfChaletsViewModel;
    ChaletListIteamModel modelChalet;
    RecyclerView rec_chalitlist;

    AdapterChaletlistOwner adapter;
    DatabaseReference ChaletsRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        listOfChaletsViewModel = new ViewModelProvider(this).get(ListOfChaletsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listofchalets, container, false);
//        textView = root.findViewById(R.id.text_home);
        rec_chalitlist = root.findViewById(R.id.rec_chalitlist);


        ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Chalet");


        ArrayList<ChaletListIteamModel> models = new ArrayList<>();
        rec_chalitlist.setLayoutManager(new LinearLayoutManager(getContext()));



        ChaletsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(getContext(),dataSnapshot.getValue()+"", Toast.LENGTH_SHORT).show();
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    modelChalet = snapshot.getValue(ChaletListIteamModel.class);
//                    Log.d("TT", modelChalet.getPrice() + "");
//                    Log.d("TT", modelChalet.getAddress() + "");
//                    Log.d("TT", modelChalet.getImage() + "");

                    models.add(modelChalet);

                }
                adapter = new AdapterChaletlistOwner(models, getContext());
                rec_chalitlist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new AdapterChaletlistOwner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), DetailsTenantActivity.class);
                        intent.putExtra("chaletId", models.get(position).getChaletId() + "");
                        getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return root;

    }







    }

