package com.example.cartravels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import com.example.cartravels.event.search.afterSearchActivity;
import com.example.cartravels.event.search.eventSearchDialog;
import com.example.cartravels.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomePage extends AppCompatActivity implements eventSearchDialog.eventSearchListner {
    SharedPreferences pref;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = getSharedPreferences("userPreferences",MODE_PRIVATE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                                .setOpenableLayout(drawer)
                                .build();
        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                eventSearchDialog searchDialog = new eventSearchDialog();
                searchDialog.show(getSupportFragmentManager(),"eventSearchDialog");
                return true;
            case R.id.action_refresh:
                Fragment frag = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                assert frag != null;
                getSupportFragmentManager().beginTransaction().detach(frag).attach(frag).commit();
            case R.id.action_settings:
                Log.e("logout","----------------------------------");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void getSearchInfo(String dest, String arri, String date) {
        Intent intent = new Intent(this, afterSearchActivity.class);
        intent.putExtra("DeptPlace",dest);
        intent.putExtra("ArriPlace",arri);
        intent.putExtra("DateForTravel",date);
        startActivity(intent);
    }

    public void onClickLogoutNav(MenuItem item){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("UserType", null);
        editor.putBoolean("LoggedIn", false);
        editor.apply();
        startActivity(new Intent(HomePage.this, LoginActivity.class));
        finish();
    }

}