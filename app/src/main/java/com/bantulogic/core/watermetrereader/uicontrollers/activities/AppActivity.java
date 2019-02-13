package com.bantulogic.core.watermetrereader.uicontrollers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.viewmodels.AuthorizationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.ui.NavigationUI;

public class AppActivity extends AppCompatActivity {
    private boolean IS_LOGGED_IN=false;
    public AuthorizationViewModel mAuthorizationViewModel;
    public  @Nullable Authorization mAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Observe Auth
        observeSharedAuthorizationViewModel();
//        if (mAuthorization == null){
//            LiveData<Authorization> auth = mAuthorizationViewModel.getLoggedInUserAuth();
//            if (auth != null && !auth.getValue().isTokenExpired()){
//                mAuthorization = auth.getValue();
//                mAuthorizationViewModel.setLoggedInUserAuth(auth.getValue());
//            }
//        }
        Log.i("Chaiwa", "onCreate->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        setContentView(R.layout.activity_app);

        //Setup Bottom Navigation Menu
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.app_nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom_nav_view);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        Log.d("CurrentDestinationB4", navController.getCurrentDestination().getLabel().toString());
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d("CurrentDestination", String.valueOf(controller.getCurrentDestination().getLabel()));
                if (destination.getId() != R.id.dest_login_fragment){
                    Log.i("DestinationChanged", "DestNotLogIn: "+destination.getLabel());
                    boolean isAuthNull = (mAuthorization == null);
                    Log.i("DestinationChanged", "IsAuthNull?: "+ isAuthNull);
                    if((mAuthorization != null && !mAuthorization.isLoggedIn()) || mAuthorization == null){
                        if (mAuthorization != null){
                            Log.i("CurrentAuth", "isLoggedIn?: "+mAuthorization.isLoggedIn());
                        }
                        bottomNavigationView.setVisibility(View.GONE);
                        controller.navigate(R.id.start_login);
                    }
                    else {
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        Log.i("CurrentAuth", "isLoggedIn?: "+mAuthorization.isLoggedIn());
                    }

                }
            }
        });
    }

    private void observeSharedAuthorizationViewModel(){
        mAuthorizationViewModel = ViewModelProviders.of(this).get(AuthorizationViewModel.class);
        mAuthorizationViewModel.getLoggedInUserAuth().observe(this, new Observer<Authorization>() {
            @Override
            public void onChanged(Authorization authorization) {
//                if (mAuthorization != null){
                    mAuthorizationViewModel.setLoggedInUserAuth(authorization);
                    mAuthorization = authorization;

//                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        Log.i("onNavigateUp", "onSupportNavigateUp->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
//        return super.onSupportNavigateUp();
        return Navigation.findNavController(this, R.id.app_nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
//        if (Navigation.findNavController(this, R.id.app_nav_host_fragment).getCurrentDestination().getId() == R.id.dest_login_fragment){
//            this.finish();
//        }
        Log.i("onBackPressed", "onBackPressed->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onBackPressed();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Log.i("onPostCreate", "onPostCreate->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPostResume() {
        Log.i("onPostResume", "onPostResume->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        Log.i("onStart", "onStart->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i("onStop", "onStop->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("onDestroy", "onDestroy->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i("onPause", "onPause->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("onResume", "onResume->IsLoggedIn?: "+ (mAuthorization !=null? mAuthorization.isLoggedIn() : ""));
        super.onResume();
    }
}
