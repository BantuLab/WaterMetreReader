package com.bantulogic.core.watermetrereader.uicontrollers.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.MetreAccount;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.uicontrollers.adapters.MetreAccountsRecyclerViewAdapter;
import com.bantulogic.core.watermetrereader.viewmodels.AuthorizationViewModel;
import com.bantulogic.core.watermetrereader.viewmodels.MetreAccountViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MetreAccountsFragment extends Fragment {
    private AuthorizationViewModel mAuthorizationViewModel;
    public Authorization mAuthorization;


    private MetreAccountViewModel mMetreAccountViewModel;

    private RecyclerView mMetreAccountsRecyclerView;
    private LinearLayoutManager mMetreAccountsRecyclerViewLayoutManager;
    private MetreAccountsRecyclerViewAdapter mMetreAccountsRecyclerViewAdapter;

    public MetreAccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_metre_accounts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Initialize RecyclerView and Observe Metre Accounts Data From The ViewModel
        observeSharedAuthorizationViewModel();
        initializeMetreAccountsRecyclerView(view);
//        initializeMetreAccountViewModel();
//        observeMetreAccountsData();
    }

    private void initializeMetreAccountsRecyclerView(View view) {
        mMetreAccountsRecyclerView = view.findViewById(R.id.rv_metre_ccounts_list);
        //Set the RecyclerView Layout Manager
        mMetreAccountsRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        mMetreAccountsRecyclerView.setLayoutManager(mMetreAccountsRecyclerViewLayoutManager);
        //Set the RecyclerView Adapter
        mMetreAccountsRecyclerViewAdapter = new MetreAccountsRecyclerViewAdapter(getContext());
        mMetreAccountsRecyclerView.setAdapter(mMetreAccountsRecyclerViewAdapter);
    }

    private void observeSharedAuthorizationViewModel(){
        mAuthorizationViewModel = ViewModelProviders.of(getActivity()).get(AuthorizationViewModel.class);
        mAuthorizationViewModel.getLoggedInUserAuth().observe(this, new Observer<Authorization>() {
            @Override
            public void onChanged(Authorization authorization) {
                mAuthorization = authorization;
            }
        });
    }

    private void observeMetreAccountsData() {
        mMetreAccountViewModel = ViewModelProviders.of(getActivity()).get(MetreAccountViewModel.class);
        mMetreAccountViewModel.getMetreAccounts().observe(this, new Observer<List<MetreAccount>>() {
            @Override
            public void onChanged(@Nullable List<MetreAccount> metreAccounts) {
                //Update the cached copy of the metre accounts in the adapter
                mMetreAccountsRecyclerViewAdapter.setMetreAccounts(metreAccounts);
            }
        });
    }
}