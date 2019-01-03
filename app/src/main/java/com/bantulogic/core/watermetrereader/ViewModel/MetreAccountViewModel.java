package com.bantulogic.core.watermetrereader.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.Data.Repository.MetreAccountRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MetreAccountViewModel extends AndroidViewModel {

    private MutableLiveData<List<MetreAccount>> metreAccounts;
    private final MetreAccountRepository mMetreAccountRepository;

    //When used as shared list for the detail fragment to use same view model
    private final MutableLiveData<MetreAccount> mSelected = new MutableLiveData<>();
    public void select(MetreAccount metreAccount){
        mSelected.setValue(metreAccount);
    }
    public LiveData<MetreAccount> getSelected(){
        return mSelected;
    }

    public MetreAccountViewModel(@NonNull Application application) {
        super(application);
        mMetreAccountRepository = new MetreAccountRepository(application);

    }

    public LiveData<List<MetreAccount>> getMetreAccounts(){
        if(metreAccounts == null){
            metreAccounts = new MutableLiveData<>();
            loadMetreAccounts();
        }
        return metreAccounts;
    }

    private void loadMetreAccounts() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                metreAccounts = mMetreAccountRepository.getMetreAccountsFromWebAPI();
                return null;
            }
        }.execute();
    }


}
