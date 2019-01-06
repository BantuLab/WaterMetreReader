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
//region NOTES
    /**
     * If the user exits the app, the View will be gone so the ViewModel is not observed anymore.
     * If the repository is a singleton or otherwise scoped to the application, the repository will
     * not be destroyed until the process is killed. This will only happen when the system needs
     * resources or the user manually kills the app. If the repository is holding a reference to a
     * callback in the ViewModel, the ViewModel will be temporarily leaked.
     *
     * This leak is not a big deal if the ViewModel is light or the operation is guaranteed to
     * finish quickly. However, this is not always the case.
     * Ideally, ViewModels should be free to go whenever they don’t have any Views observing them
     *
     * You have many options to achieve this:
     *
     * With ViewModel.onCleared() you can tell the repository to drop the callback to the ViewModel.
     * In the repository you can use a WeakReference or you can use an Event Bus (both easy to
     * misuse and even considered harmful).
     * Use the LiveData to communicate between the Repository and ViewModel in a similar way to
     * using LiveData between the View and the ViewModel.
     *
     * LiveData in repositories
     * To avoid leaking ViewModels and callback hell, repositories can be observed like this:
     * -When the ViewModel is cleared or when the lifecycle of the view is finished,
     * the subscription is cleared
     *
     * There’s a catch if you try this approach: how do you subscribe to the Repository from the
     * ViewModel if you don’t have access to the LifecycleOwner? Using Transformations is a very
     * convenient way to solve this. Transformations.switchMap lets you create a new LiveData that
     * reacts to changes of other LiveData instances.
     * It also allows carrying over the observer Lifecycle information across the chain
     *
     *  Whenever you think you need a Lifecycle object inside a ViewModel, a Transformation
     *  is probably the solution
     *
     *  Extending LiveData
     *  The most common use case for LiveData is using MutableLiveData in ViewModels and exposing
     *  them as LiveData to make them immutable from the observers.
     *
     *  If you need more functionality, extending LiveData will let you know when there are
     *  active observers. This is useful when you want to start listening to a location or sensor
     *  service, for example.
     *  public class MyLiveData extends LiveData<MyData> {
     *
     *     public MyLiveData(Context context) {
     *         // Initialize service
     *     }
     *
     *     @Override
     *     protected void onActive() {
     *         // Start listening
     *     }
     *
     *     @Override
     *     protected void onInactive() {
     *         // Stop listening
     *     }
     * }
     *
     * You don’t usually extend LiveData. Let your activity or fragment tell the ViewModel when
     * it’s time to start loading data
     *
     */
//endregion
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
//            metreAccounts = new MutableLiveData<>();
            metreAccounts = mMetreAccountRepository.getMetreAccountsFromWebAPI();
        }
        return metreAccounts;
    }
}
