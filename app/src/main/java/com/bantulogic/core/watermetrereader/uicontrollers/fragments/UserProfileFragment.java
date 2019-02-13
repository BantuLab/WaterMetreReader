package com.bantulogic.core.watermetrereader.uicontrollers.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.helpers.MetreReaderApp;
import com.bantulogic.core.watermetrereader.R;
import com.bantulogic.core.watermetrereader.viewmodels.UserProfileViewModel;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel mUserProfileViewModel;
    private static final String userId =  MetreReaderApp.getLoggedInUserAuthorization().getValue().getSub().toString();
    //Application Global State Variables
    private Authorization mAuthorization;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserProfileViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        mUserProfileViewModel.init(userId);

        mUserProfileViewModel.getUser().observe(this, user -> {
            //Update the ui here: note we have use a lambda expression here as jdk 1.8
            //Every time the user profile data is updated, the onChanged() callback is invoked,
            //and the UI is refreshed.
        });
    }

}
