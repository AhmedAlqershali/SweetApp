package com.example.sweetapp.ui.listFavoritesFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweetapp.Adapter.AdapterChaletlistOwner;
import com.example.sweetapp.Adapter.AdapterChaletlistTenant;
import com.example.sweetapp.Adapter.AdapterFavorits;
import com.example.sweetapp.DetailsActivity;
import com.example.sweetapp.DetailsTenantActivity;
import com.example.sweetapp.R;
import com.example.sweetapp.database.Database;
import com.example.sweetapp.model.ChaletListIteamModel;
import com.example.sweetapp.model.Favorites;
import com.example.sweetapp.ui.listOfChalets.ListOfChaletsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListFavoritesFragment extends Fragment {

  
    RecyclerView rec_favlist;
    AdapterFavorits adapter;
    ArrayList<Favorites> models;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        listOfChaletsViewModel = new ViewModelProvider(this).get(ListOfChaletsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listfavorites, container, false);
//        textView = root.findViewById(R.id.text_home);

        rec_favlist = root.findViewById(R.id.rec_favlist);



         models = new ArrayList<>();
        Database database = new Database(getContext());
//        favorites = new ChaletListIteamModel();
         models = database.getFavorites();





                adapter = new AdapterFavorits(models, getContext());
                rec_favlist.setLayoutManager(new LinearLayoutManager(getContext()));
                rec_favlist.setAdapter(adapter);
                adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new AdapterFavorits.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailsTenantActivity.class);
                intent.putExtra("chaletTenantId", models.get(position).getChaletId() + "");
                getContext().startActivity(intent);
            }
        });



        return root;

    }


}

