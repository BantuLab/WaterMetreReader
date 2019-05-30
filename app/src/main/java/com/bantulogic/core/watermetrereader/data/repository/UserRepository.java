package com.bantulogic.core.watermetrereader.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.UserDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.User;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ApiResponse;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.NetworkBoundResource;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.RateLimiter;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ServiceGenerator;
import com.bantulogic.core.watermetrereader.data.datasource.network.UserWebAPI;
import com.bantulogic.core.watermetrereader.helpers.AppExecutors;
import com.bantulogic.core.watermetrereader.helpers.Resource;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/*
*The UserRepository exposes the data to the UI layer.
* To ensure that the UI uses the list of MetreAccounts only after the database has been pre-populated,
 * a MediatorLiveData object is used. This observes the changes of the list of MetreAccounts and only
 * forwards it when the database is ready to be used.
 * NOTE:
 * The createService method takes a serviceClass, which is the annotated interface for API requests,
 * as a parameter and creates a usable client from it. On the resulting client you'll be able to
 * execute your network requests.
 * Repository modules handle data operations. They provide a clean API so that the rest of the app
 * can retrieve this data easily. They know where to get the data from and what API calls to make
 * when data is updated. You can consider repositories to be mediators between different data
 * sources, such as persistent models, web services, and caches.
 *
 * our UserRepository implementation saves web service responses into the database.
 * Changes to the database then trigger callbacks on active LiveData objects.
 * Using this model, the database serves as the single source of truth, and other parts of the app
 * access it using our UserRepository.
 * Regardless of whether you use a disk cache, we recommend that your repository designate a
 * data source as the single source of truth for the rest of your app.
 *
 * We can use one of the following strategies to display a consistent data-updating status in the
 * UI, regardless of where the request to update the data came from:
 * -Change getUser() to return an object of type LiveData. This object would include the status of
 * the network operation.
 * For an example, see the NetworkBoundResource implementation in the
 * android-architecture-components GitHub project.
 * -Provide another public function in the UserRepository class that can return the refresh status
 * of the User. This option is better if you want to show the network status in your UI only when
 * the data-fetching process originated from an explicit user action, such as pull-to-refresh.
 */
public class UserRepository {
    private static final Long FRESH_TIMEOUT = System.currentTimeMillis();
    private UserDAO mUserDAO;
    private UserWebAPI mUserWebAPI;
    private Executor mExecutor;

    private LiveData<List<User>> mAllUsers;
    private LiveData<User> mUser;
    //Research about implementing RateLimiter for Android Client Apps
    RateLimiter<String> mUserRateLimiter = new RateLimiter<String>(10, TimeUnit.MINUTES);

    public UserRepository(Application application, Executor executor){
        AppDatabase db  = AppDatabase.getDatabase(application);

        this.mUserDAO = db.userDAO();
        this.mAllUsers = mUserDAO.getAllUsers();
        this.mExecutor = executor; //pass Executors.newSingleThreadExecutor();

        mUserWebAPI = ServiceGenerator.createService(UserWebAPI.class,"some_token");

    }

/*  Wrapper methods for the ViewModels:
*   Get the list of users, metre accounts and metre readings from the database
*   and get notified when the data changes.
*   Note: Room will take care of executing the get methods on a background thread but for the
*   Update, Create/Insert, and Delete methods we have to implement them on our own using the
*   background thread AsyncTask since Room does not allow database operations on the main thread
*/
//region USER API IMPLEMENTATION
    public LiveData<List<User>> getAllUsers(){
        return mAllUsers;
    }

//    public LiveData<User> getUser(String userId){
//        //TODO Fix the User Retrieval Workflow
//        //Here we can check if logged-in, then we return the logged-in user. At login we check if
//        //there is network we try to get the user from the REST API then cache the user to local DB,
//        //so if requesting the user and offline then authenticate using local cached user/profile
//        //otherwise query the user from the network REST API
//
//        //Refresh User
//        refreshUser(userId);
//        // Returns a LiveData object directly from the database.
//        mUser = mUserDAO.getUser(userId);
//        return mUser;
//    }
    public LiveData<Resource<User>> getUser(final String userId){
        return new NetworkBoundResource<User, User>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull User item) {
                mUserDAO.insertUser(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return mUserRateLimiter.canFetch(userId);
//                return true;
//                return mUserRateLimiter.canFetch(userId)
//                        && (data == null || !isFresh(data));
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return mUserDAO.getUser(userId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return mUserWebAPI.getUser(userId);
            }

            @Override
            protected void onFetchFailed() {
                mUserRateLimiter.reset(userId);

            }
        }.getAsLiveData();
    }
//endregion
}

