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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ProgressBar
        mProgressBar = view.findViewById(R.id.progressBar);

        mAuthorizationViewModel = ViewModelProviders.of(getActivity()).get(AuthorizationViewModel.class);
        mNavController = Navigation.findNavController(view);

        mUsername = view.findViewById(R.id.etUsername);
        mPassword = view.findViewById(R.id.etPassword);
        mBtnLogin = view.findViewById(R.id.btnLogin);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                Log.i("onClick","ClickedLogin");

                //1. Validate fields
                //2. Call Login
               mAuthorizationViewModel.login(username, password).observe(getActivity(), new Observer<Resource<Authorization>>() {
                   @Override
                   public void onChanged(Resource<Authorization> auth) {
                       if (auth.status == Status.SUCCESS){

                           Log.i("onChangedAuth","Auth.Status: "+ auth.status);
                           //Set loggedInUser
                           //mAuthData = mAuthorization.data;
                           mAuthorizationViewModel.setLoggedInUserAuth(auth.data);
                           //mAuthData = mAuthorization.data;
                           //Log.i("AuthToken", mAuthData.getToken());
                           Log.i("onChangedAuth","Token: "+auth.data.getToken());
                           //Pop back to whatever fragment called the login fragment
                           mNavController.popBackStack(R.id.dest_login_fragment, true);
                       }
                       else {
                           if (auth.status == Status.LOADING){
                               mProgressBar.setVisibility(View.VISIBLE);
                           }
                           else {
                               mProgressBar.setVisibility(View.INVISIBLE);
                               Snackbar.make(getView(), "Error: "+ auth.message + "; Status: "+auth.status, Snackbar.LENGTH_LONG).show();
                           }
                           Log.i("onChangedAuth","Auth.Status: "+ auth.status + "; Message: " + auth.message);
                       }
                   }
               });
            }
        });
    }

}
