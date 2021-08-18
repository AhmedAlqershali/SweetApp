package com.example.sweetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sweetapp.ui.accountManagement.AccountManagementFragment;
import com.example.sweetapp.ui.listFavoritesFragment.ListFavoritesFragment;
import com.example.sweetapp.ui.listOfChaletsTenant.ListOfChaletsTenantFragment;
import com.example.sweetapp.ui.statusOfMyOrders.statusOfMyOrdersFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class TenantMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main);

        mToolbar = findViewById(R.id.toolbar_tenant);
        setSupportActionBar(mToolbar);

        mAuth = FirebaseAuth.getInstance();

        drawer = findViewById(R.id.drawer_layout_tenant);
        navigationView = findViewById(R.id.nav_view_tenant);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_account_management_tenant, R.id.nav_list_of_chalets_tenant, R.id.nav_list_of_favorite_tenant)
                .setDrawerLayout(drawer)
                .build();

        navigationView.bringToFront();
        ActionBarDrawerToggle dToggle = new ActionBarDrawerToggle(this,drawer,mToolbar,R.string.navigation_drawer_open_tenant,R.string.navigation_drawer_close_tenant);
        drawer.addDrawerListener(dToggle);
        dToggle.syncState();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_tenant);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

//        Intent service = new Intent(TenantMainActivity.this, ListenOrderService.class);
//        startService(service);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tenant_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_tenant);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_account_management_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_tenant, new AccountManagementFragment()).commit();
                mToolbar.setTitle("إدارة الحساب");
                break;
            case R.id.nav_list_of_chalets_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_tenant, new ListOfChaletsTenantFragment()).commit();
                mToolbar.setTitle("قائمة الشاليهات");

                break;
            case R.id.nav_list_of_favorite_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_tenant, new ListFavoritesFragment()).commit();
                mToolbar.setTitle("المفضلات");
                break;

            case R.id.nav_list_of_myrequests_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_tenant, new statusOfMyOrdersFragment()).commit();
                mToolbar.setTitle("قائمة طلباتي");
                break;
            case R.id.nav_logout_tenant:
                mAuth.signOut();
                sendUserToChooseTypeActivity();


        }
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    private void sendUserToChooseTypeActivity() {
        Intent regIntent = new Intent(TenantMainActivity.this, ChooseTypeActivity.class);
        startActivity(regIntent);
        finish();
    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_tenant, new ListOfChaletsTenantFragment()).commit();
//    }


}