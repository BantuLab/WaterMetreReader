package com.bantulogic.core.watermetrereader.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bantulogic.core.watermetrereader.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.ui.NavigationUI;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        //Setup Bottom Navigation Menu
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.app_nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom_nav_view);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               Log.d("onDestinationChanged", Integer.toString(destination.getParent().getId()));
                if (destination.getParent().getId() == R.id.nested_home_nav_graph){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                else {
                    bottomNavigationView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
//        return super.onSupportNavigateUp();
        return Navigation.findNavController(this, R.id.app_nav_host_fragment).navigateUp();
    }
}
