package com.bantulogic.core.watermetrereader.ViewModel;

import android.app.Application;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.User;
import com.bantulogic.core.watermetrereader.Data.Repository.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;
    private LiveData<List<User>> mAllUsers;
    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mAllUsers = mUserRepository.getAllUsers();
    }

//    Wrapper methods for the UI
    public LiveData<List<User>> getAllUsers(){
        return mAllUsers;
    }
}
