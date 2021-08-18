package com.example.sweetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.sweetapp.ui.accountManagement.AccountManagementFragment;
import com.example.sweetapp.ui.listOfChalets.ListOfChaletsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ListOfChaletsFragment()).commit();

        mAuth = FirebaseAuth.getInstance();
        
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddChaletActivity.class);
                startActivity(intent);
            }
        });
         drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();

        navigationView.bringToFront();
        ActionBarDrawerToggle dToggle = new ActionBarDrawerToggle(this,drawer,mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(dToggle);
        dToggle.syncState();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_account_management:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AccountManagementFragment()).commit();
                mToolbar.setTitle("إدارة الحساب");
                break;
            case R.id.nav_list_of_chalets:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ListOfChaletsFragment()).commit();
                mToolbar.setTitle("قائمة الشالهات");
                break;
            case R.id.nav_list_of_requests:
                mToolbar.setTitle("قائمة الطلبات");
                Intent orderIntent = new Intent(MainActivity.this,OrderStatusActivity.class);
                startActivity(orderIntent);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                sendUserToChooseTypeActivity();

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    private void sendUserToChooseTypeActivity() {
        Intent regIntent = new Intent(MainActivity.this, ChooseTypeActivity.class);
        startActivity(regIntent);
    }
}