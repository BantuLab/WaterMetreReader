package com.bantulogic.core.watermetrereader.UIController.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.Data.Repository.MetreAccountRepository;
import com.bantulogic.core.watermetrereader.Data.Repository.UserRepository;
import com.bantulogic.core.watermetrereader.Helpers.MetreReaderApp;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.ViewModel.AuthorizationViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button mBtnLogin;
    private AuthorizationViewModel mAuthorizationViewModel;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnLogin = view.findViewById(R.id.btnLogin);

        mBtnLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nested_home_nav_graph, null));
    }
}
