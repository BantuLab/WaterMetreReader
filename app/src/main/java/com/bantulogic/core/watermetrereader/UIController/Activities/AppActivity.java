package com.bantulogic.core.watermetrereader.UIController.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Authorization;
import com.bantulogic.core.watermetrereader.Helpers.MetreReaderApp;
import com.bantulogic.core.watermetrereader.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.ui.NavigationUI;

public class AppActivity extends AppCompatActivity {
    private boolean IS_LOGGED_IN=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        //Setup Bottom Navigation Menu
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.app_nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom_nav_view);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        if (MetreReaderApp.getLoggedInUserAuthorization() != null){
            IS_LOGGED_IN = true;
        }
        else {
            IS_LOGGED_IN = false;
        }
        Log.d("CurrentDestinationBefore", navController.getCurrentDestination().getLabel().toString());
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               Log.d("CurrentDestination", String.valueOf(controller.getCurrentDestination().getLabel()));

                if (destination.getId() == R.id.dest_metre_accounts_fragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                if (destination.getId() == R.id.dest_metre_accounts_fragment){
                    if (!IS_LOGGED_IN){
                        controller.navigate(R.id.start_login);
                    }

                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
//        return super.onSupportNavigateUp();
        return Navigation.findNavController(this, R.id.app_nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
        if (Navigation.findNavController(this, R.id.app_nav_host_fragment).getCurrentDestination().getId() == R.id.dest_login_fragment){
            this.finish();
        }
        super.onBackPressed();
    }
}
