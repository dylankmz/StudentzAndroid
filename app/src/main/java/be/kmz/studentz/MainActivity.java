package be.kmz.studentz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidThreeTen.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavController = Navigation.findNavController(this, R.id.nav_host);
        mAppBarConfiguration = new AppBarConfiguration.Builder().build();

        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}