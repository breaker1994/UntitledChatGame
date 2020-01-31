package com.trinarr.phonegameconcept.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trinarr.phonegameconcept.R;

public class ScreenTabs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_tabs);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                openScreen(menuItem.getItemId());
                return false;
            }
        });

        openScreen(R.id.tab_chats);
    }

    private void openScreen(int type) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment;
        int numType;

        switch (type) {
            default:
            case R.id.tab_chats:
                numType = 0;
                fragment = new TabChats();
                break;
            case R.id.tab_settings:
                numType = 1;
                return;
                //fragment = new TabNews();
                //break;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentManager.getFragments().size() == 0) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        }
        else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(numType);
        menuItem.setChecked(true);
    }
}
