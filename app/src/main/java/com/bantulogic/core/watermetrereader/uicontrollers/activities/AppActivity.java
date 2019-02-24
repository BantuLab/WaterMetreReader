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
    //    private LiveData<Authorization> mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_app);

        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.app_nav_host_fragment);
        mNavController = mNavHostFragment.getNavController();

        mBottomNavigationView = findViewById(R.id.nav_bottom_nav_view);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);

        if (Build.VERSION.SDK_INT>9){

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mAuthorizationViewModel = ViewModelProviders.of(this).get(AuthorizationViewModel.class);

        mAuthorizationViewModel.init();

        mAuthorizationViewModel.getLoggedInUserAuth().observe(this, new Observer<Authorization>() {
            @Override
            public void onChanged(@Nullable Authorization authorization) {
                mBinding.setViewModel(authorization);
                mIsLoggedIn = authorization == null? false: authorization.isLoggedIn();
                mIsTokenExpired = authorization == null ? true : authorization.isTokenExpired();
                if(!mIsLoggedIn || mIsTokenExpired){
                    if (authorization != null){
                        if (authorization.isTokenExpired()){
                        }
                        Log.i("CurrentAuth", "mIsLoggedIn?: "+authorization.isLoggedIn());
                        Log.i("CurrentAuth", "isTokenExpired?: "+authorization.isTokenExpired());
                        Log.i("CurrentAuth", "tokenCurrentTime?: "+ Calendar.getInstance().getTime());
                        Log.i("CurrentAuth", "tokenIssuedAt?: "+ authorization.getIat());
                        Log.i("CurrentAuth", "tokenExpiresAt?: "+ authorization.getExp());
                    }
                    mNavController.navigate(R.id.start_login);
                }
            }
        });



        //Observe changes to auth
        mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.i("onDestChanged", "newDestination: "+destination.getLabel());
                if (destination.getId() != R.id.dest_login_fragment){
                    if (!mIsLoggedIn || mIsTokenExpired){

//                        controller.navigate(R.id.start_login);
                    }
                }
                }

        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("onNavigateUp", "onSupportNavigateUp->IsLoggedIn?: "+ mIsLoggedIn);
        return Navigation.findNavController(this, R.id.app_nav_host_fragment).navigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "onBackPressed->IsLoggedIn?: "+ mIsLoggedIn);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("onPostCreate", "onPostCreate->IsLoggedIn?: "+ mIsLoggedIn);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i("onPostResume", "onPostResume->IsLoggedIn?: "+ mIsLoggedIn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("onStart", "onStart->IsLoggedIn?: "+ mIsLoggedIn);
    }

    @Override
    protected void onStop() {
        Log.i("onStop", "onStop->IsLoggedIn?: "+ mIsLoggedIn);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", "onDestroy->IsLoggedIn?: "+ mIsLoggedIn);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause", "onPause->IsLoggedIn?: "+ mIsLoggedIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "onResume->IsLoggedIn?: "+ mIsLoggedIn);
    }
}