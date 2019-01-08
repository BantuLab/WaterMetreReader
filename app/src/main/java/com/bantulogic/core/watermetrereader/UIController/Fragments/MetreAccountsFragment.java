package com.bantulogic.core.watermetrereader.UIController.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.UIController.Adapters.MetreAccountsRecyclerViewAdapter;
import com.bantulogic.core.watermetrereader.ViewModel.MetreAccountViewModel;

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
        initializeMetreAccountsRecyclerView(view);
        initializeMetreAccountViewModel();
        observeMetreAccountsData();
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

    private void initializeMetreAccountViewModel() {
        //region NOTES

        /**
         * The MetreAccountsViewModel is scoped to the AppActivity and shared with other components
         * in the App such as other Fragments.
         *
         * Add an observer for the LiveData returned by the ViewModel's getMetreAccounts().
         * It currently returns results from the Web, but the repo can be changed to return from
         * local cache/database
         * The onChanged() method fires when the observed data changes and the activity is in the
         * foreground.
         **/

        //endregion
        mMetreAccountViewModel = ViewModelProviders.of(getActivity()).get(MetreAccountViewModel.class);
    }

    private void observeMetreAccountsData() {
        mMetreAccountViewModel.getMetreAccounts().observe(this, new Observer<List<MetreAccount>>() {
            @Override
            public void onChanged(@Nullable List<MetreAccount> metreAccounts) {
                //Update the cached copy of the metre accounts in the adapter
                mMetreAccountsRecyclerViewAdapter.setMetreAccounts(metreAccounts);
            }
        });
    }
}