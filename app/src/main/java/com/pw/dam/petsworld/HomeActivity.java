package com.pw.dam.petsworld;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.pw.dam.petsworld.Fragments.AddFragment;
import com.pw.dam.petsworld.Fragments.HomeFragment;
import com.pw.dam.petsworld.Fragments.ListFragment;
import com.pw.dam.petsworld.Fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView MainNav;
    private FrameLayout MainFrame;
    private HomeFragment homeFragment;
    private AddFragment addFragment;
    private ListFragment listFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MainNav = findViewById(R.id.main_nav);
        MainFrame = findViewById(R.id.main_frame);
        homeFragment = new HomeFragment();
        addFragment = new AddFragment();
        listFragment = new ListFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);

        MainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        MainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_add:
                        MainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(addFragment);
                        return true;

                    case R.id.nav_list:
                        MainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(listFragment);
                        return true;

                    case R.id.nav_profile:
                        MainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(profileFragment);
                        return true;

                    default:
                        return false;
                }
            }


        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
