package com.bantulogic.core.watermetrereader.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.os.Bundle;

import com.bantulogic.core.watermetrereader.R;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
    }

    @Override
    public boolean onSupportNavigateUp() {
//        return super.onSupportNavigateUp();
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
