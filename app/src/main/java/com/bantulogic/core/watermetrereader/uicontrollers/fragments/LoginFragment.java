package com.bantulogic.core.watermetrereader.uicontrollers.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.helpers.Resource;
import com.bantulogic.core.watermetrereader.helpers.Status;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.viewmodels.AuthorizationViewModel;
import com.google.android.material.snackbar.Snackbar;
/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button mBtnLogin;
    private AuthorizationViewModel mAuthorizationViewModel;
    private EditText mUsername;
    private TextView mPassword;
    private NavController mNavController;
    private ProgressBar mProgressBar;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuthorizationViewModel = ViewModelProviders.of(getActivity()).get(AuthorizationViewModel.class);
        mNavController = Navigation.findNavController(view);
        mProgressBar = view.findViewById(R.id.progressBar);
        mUsername = view.findViewById(R.id.etUsername);
        mPassword = view.findViewById(R.id.etPassword);
        mBtnLogin = view.findViewById(R.id.btnLogin);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
              mAuthorizationViewModel.login(username, password).observe(getActivity(), new Observer<Resource<Authorization>>() {
                   @Override
                   public void onChanged(Resource<Authorization> auth) {
                       Log.i("onChangedAuth","Auth.Status: "+ auth.status + "; Message: " + auth.message);
                       if (auth.status == Status.SUCCESS){
                           if (!auth.data.isTokenExpired() && !auth.data.isLoggedIn()){
                               mAuthorizationViewModel.loginCurrentUser(auth.data);
                           }
                       }
                       else {
                           if (auth.status == Status.LOADING){
                               mProgressBar.setVisibility(View.VISIBLE);
                           }
                           else {
                               mProgressBar.setVisibility(View.INVISIBLE);
                               Snackbar.make(getView(), "Error: "+ auth.message + "; Status: "+auth.status, Snackbar.LENGTH_LONG).show();
                           }
                       }
                   }
               });
            }
        });
    }
}