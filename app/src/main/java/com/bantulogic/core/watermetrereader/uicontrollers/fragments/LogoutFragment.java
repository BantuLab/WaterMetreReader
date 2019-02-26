package com.bantulogic.core.watermetrereader.uicontrollers.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.viewmodels.AuthorizationViewModel;
import com.bantulogic.core.watermetrereader.databinding.FragmentLogoutBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {

    private AuthorizationViewModel mAuthorizationViewModel;
    private Authorization mAuth;
    private FragmentLogoutBinding mBinding;

    public LogoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuthorizationViewModel = ViewModelProviders.of(getActivity()).get(AuthorizationViewModel.class);
        mAuthorizationViewModel.getLoggedInUserAuth().observe(getActivity(), new Observer<Authorization>() {
            @Override
            public void onChanged(Authorization authorization) {
                mAuth = authorization;
            }
        });
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_logout,container,false);
        View view = mBinding.getRoot();
        mBinding.setViewModel(mAuth);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_logout_fragment)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuthorizationViewModel.logoutCurrentUser();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Navigation.findNavController(view).popBackStack();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                })
                .show();
    }
}