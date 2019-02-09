package com.bantulogic.core.watermetrereader.UIController.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Authorization;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.Data.Repository.MetreAccountRepository;
import com.bantulogic.core.watermetrereader.Data.Repository.UserRepository;
import com.bantulogic.core.watermetrereader.Helpers.MetreReaderApp;
import com.bantulogic.core.watermetrereader.Helpers.Resource;
import com.bantulogic.core.watermetrereader.Helpers.Status;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.ViewModel.AuthorizationViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button mBtnLogin;
    private AuthorizationViewModel mAuthorizationViewModel;
    private Resource<Authorization> mAuthorization;
    private Authorization mAuthData;

    private EditText mUsername;
    private TextView mPassword;


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
        mAuthorizationViewModel = ViewModelProviders.of(getActivity()).get(AuthorizationViewModel.class);

        mUsername = view.findViewById(R.id.etUsername);
        mPassword = view.findViewById(R.id.etPassword);
        mBtnLogin = view.findViewById(R.id.btnLogin);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                Log.i("Chaiwa","ClickedLogin");
//                Snackbar.make(view, "Error: "+Status.ERROR, LENGTH_SHORT).show();
                //1. Validate fields

                //2. Call Login
               mAuthorizationViewModel.login(username, password).observe(getActivity(), new Observer<Resource<Authorization>>() {
                   @Override
                   public void onChanged(Resource<Authorization> authorizationResource) {
                       mAuthorization = authorizationResource;
                       if (mAuthorization.status == Status.SUCCESS){
                           mAuthData = mAuthorization.data;
                           Log.i("AuthToken", mAuthData.getToken());
                           Navigation.findNavController(view).navigate(R.id.dest_metre_accounts_fragment);
                       }
                       else {
                           Snackbar.make(view, "Error: "+ mAuthorization.message + "; Status: "+mAuthorization.status, Snackbar.LENGTH_LONG).show();
                       }
                   }
               });
            }
        });

//        mBtnLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nested_home_nav_graph, null));
    }

}
