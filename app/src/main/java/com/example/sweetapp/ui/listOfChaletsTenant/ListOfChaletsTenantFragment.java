package com.example.sweetapp.ui.listOfChaletsTenant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sweetapp.Adapter.AdapterChaletlistOwner;
import com.example.sweetapp.Adapter.AdapterChaletlistTenant;
import com.example.sweetapp.AddChaletActivity;
import com.example.sweetapp.DetailsActivity;
import com.example.sweetapp.DetailsTenantActivity;
import com.example.sweetapp.FilterActivity;
import com.example.sweetapp.MainActivity;
import com.example.sweetapp.R;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.ui.listOfChalets.ListOfChaletsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListOfChaletsTenantFragment extends Fragment {

    private ListOfChaletsViewModel listOfChaletsViewModel;
    TextView textView;
    ChaletListIteamModel modelChalet;
    RecyclerView rec_chalitlist;

    AdapterChaletlistTenant adapter;
    DatabaseReference ChaletsRef;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    ArrayList<ChaletListIteamModel> models;
    ValueEventListener valueEventListener;

    private static View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root != null) {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null)
                parent.removeView(root);
        }
        try {
             root = inflater.inflate(R.layout.fragment_listofchaletstenant, container, false);

        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

//        listOfChaletsViewModel = new ViewModelProvider(this).get(ListOfChaletsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listofchaletstenant, container, false);
//        textView = root.findViewById(R.id.text_home);
        rec_chalitlist = root.findViewById(R.id.rec_chalitlist_tenant);
        ChaletsRef = FirebaseDatabase.getInstance().getReference().child("Sweet App").child("Chalet");
        mSearchField = root.findViewById(R.id.search_field);
        mSearchBtn = root.findViewById(R.id.search_btn);

       models = new ArrayList<>();
        rec_chalitlist.setLayoutManager(new LinearLayoutManager(getContext()));


        valueEventListener = ChaletsRef.addValueEventListener(new ValueEventListener() {
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
                adapter = new AdapterChaletlistTenant(models, getContext());
                rec_chalitlist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new AdapterChaletlistTenant.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), DetailsTenantActivity.class);
                        intent.putExtra("chaletTenantId", models.get(position).getChaletId() + "");
                        getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab_filter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                startActivity(intent);
            }
        });
        return root;

    }


    private void firebaseUserSearch(String searchText) {

        Toast.makeText(getContext(), "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = ChaletsRef.orderByChild("name_Chalet").startAt(searchText).endAt(searchText + "\uf8ff");
        adapter = new AdapterChaletlistTenant(models, getContext());
        rec_chalitlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        rec_chalitlist.setAdapter(adapter);
        firebaseSearchQuery.addListenerForSingleValueEvent(valueEventListener);

    }


}
