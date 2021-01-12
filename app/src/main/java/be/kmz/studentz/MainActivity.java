package be.kmz.studentz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import be.kmz.studentz.fragment.ContactFragment;
import be.kmz.studentz.fragment.SettingsFragment;
import be.kmz.studentz.fragment.StudentDetailsFragment;
import be.kmz.studentz.fragment.StudentListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(
                navListener
        );

        //ref: https://developer.android.com/guide/fragments/transactions
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.nav_host_fragment, StudentListFragment.class, null).setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);
        transaction.commit();

        AndroidThreeTen.init(this);

    }

    //ref: https://www.geeksforgeeks.org/bottomnavigationview-inandroid/
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new StudentListFragment();
                        break;
                    case R.id.nav_student:
                        selectedFragment = new StudentDetailsFragment();
                        break;
                    case R.id.nav_contact:
                        selectedFragment = new ContactFragment();
                        break;
                    case R.id.nav_settings:
                        selectedFragment = new SettingsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.nav_host_fragment,
                        selectedFragment, null).setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit).commit();
                return true;
            };

}