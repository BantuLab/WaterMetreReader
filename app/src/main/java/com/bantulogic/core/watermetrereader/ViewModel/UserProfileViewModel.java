package com.bantulogic.core.watermetrereader.ViewModel;

import android.app.Application;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.User;
import com.bantulogic.core.watermetrereader.Data.Repository.UserRepository;
import com.bantulogic.core.watermetrereader.Helpers.Resource;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserProfileViewModel extends AndroidViewModel {
    private final UserRepository mUserRepository;
    // TODO: Implement the ViewModel
    private String mUserId;
    private LiveData<Resource<User>> mUser;

    //Refactor to use dependency injection with either Dagger2 or Service Locator Pattern
    public UserProfileViewModel(@NonNull Application application){
        super(application);
        //Pass Executors to allow the repo to execute synchronous retrieval of user objects from API
        mUserRepository = new UserRepository(application, Executors.newSingleThreadExecutor());

    }

    public void init(String userId){
        if (this.mUser != null){
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        this.mUser = mUserRepository.getUser(userId);
    }

    public LiveData<Resource<User>> getUser() {
        return mUser;
    }
}
