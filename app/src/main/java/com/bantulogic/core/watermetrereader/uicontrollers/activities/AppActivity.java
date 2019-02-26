package com.bantulogic.core.watermetrereader.uicontrollers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.databinding.ActivityAppBinding;
import com.bantulogic.core.watermetrereader.viewmodels.AuthorizationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import androidx.navigation.ui.NavigationUI;

public class AppActivity extends AppCompatActivity {
    public AuthorizationViewModel mAuthorizationViewModel;
    public  @Nullable Authorization mAuthorization;
    private NavController mNavController;
    private NavHostFragment mNavHostFragment;
    private BottomNavigationView mBottomNavigationView;
    private boolean mIsLoggedIn;
    private ActivityAppBinding mBinding;
    private boolean mIsTokenExpired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_app);

        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.app_nav_host_fragment);
        mNavController = mNavHostFragment.getNavController();

        mBottomNavigationView = findViewById(R.id.nav_bottom_nav_view);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);

        mAuthorizationViewModel = ViewModelProviders.of(this).get(AuthorizationViewModel.class);

        mAuthorizationViewModel.getLoggedInUserAuth().observe(this, new Observer<Authorization>() {
            @Override
            public void onChanged(@Nullable Authorization authorization) {
                mBinding.setViewModel(authorization);
                mIsLoggedIn = authorization == null? false: authorization.isLoggedIn();
                mIsTokenExpired = authorization == null ? true : authorization.isTokenExpired();
                if(!mIsLoggedIn || mIsTokenExpired){
                    if (authorization != null){
                        Log.i("CurrentAuth", "tokenExpiresAt?: "+ authorization.getExp());
                    }
                    mNavController.navigate(R.id.start_login);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.app_nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}