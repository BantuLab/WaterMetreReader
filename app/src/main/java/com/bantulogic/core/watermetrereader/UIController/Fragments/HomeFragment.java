package com.bantulogic.core.watermetrereader.UIController.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.UIController.Adapters.MetreAccountRecyclerAdapter;
import com.bantulogic.core.watermetrereader.ViewModel.MetreAccountViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //ViewModel Reference to survive config changes
    private MetreAccountViewModel mMetreAccountViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeDisplayContent(view);
    }

    private void initializeDisplayContent(View view){
        RecyclerView recyclerViewMetreAccounts = view.findViewById(R.id.rv_metre_ccounts_list);

        //Create the Layout Manager
        LinearLayoutManager metreAccountsLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewMetreAccounts.setLayoutManager(metreAccountsLayoutManager);

        //Set the RecyclerView Adapter
        final MetreAccountRecyclerAdapter metreAccountRecyclerAdapter = new MetreAccountRecyclerAdapter(getContext());
        recyclerViewMetreAccounts.setAdapter(metreAccountRecyclerAdapter);

        //Hook up the ViewModel
        mMetreAccountViewModel = ViewModelProviders.of(getActivity()).get(MetreAccountViewModel.class);

        /*
         Add an observer for the LiveData returned by the ViewModel's getMetreAccounts().
         It currently returns results from the Web, but the repo can be changed to return from local
         cache/database
         The onChanged() method fires when the observed data changes and the activity is in the foreground.
         */
        mMetreAccountViewModel.getMetreAccounts().observe(this, new Observer<List<MetreAccount>>() {
            @Override
            public void onChanged(List<MetreAccount> metreAccounts) {
                //Update the cached copy of the metre accounts in the adapter
                metreAccountRecyclerAdapter.setMetreAccounts(metreAccounts);
            }
        });
    }

}
