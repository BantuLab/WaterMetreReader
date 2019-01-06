package com.bantulogic.core.watermetrereader.Data.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO.UserDAO;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.User;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.ApiResponse;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.NetworkBoundResource;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.ServiceGenerator;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.UserWebAPI;
import com.bantulogic.core.watermetrereader.Helpers.AppExecutors;
import com.bantulogic.core.watermetrereader.Helpers.Resource;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
//    RateLimiter<String> mUserRateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);
//    boolean shouldFetch = mUserRateLimiter.shouldFetch("KEY");

    public UserRepository(Application application, Executor executor){
        AppDatabase db  = AppDatabase.getDatabse(application);

        this.mUserDAO = db.userDAO();
        this.mAllUsers = mUserDAO.getAllUsers();
        this.mExecutor = executor; //pass Executors.newSingleThreadExecutor();

        mUserWebAPI = ServiceGenerator.createService(UserWebAPI.class,"peter@klaven","cityslicka");

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
//                return rateLimiter.canFetch(userId)
//                        && (data == null || !isFresh(data));
                return true;
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

            }
        }.getAsLiveData();
    }

//    private void refreshUser(String userId) {
//        //Runs in a background thread
//        mExecutor.execute(() -> {
//            //Check if user data was fetched recently(i.e. less than 2 minutes)
//            boolean userExists = mUserDAO.hasUser(userId, FRESH_TIMEOUT) <= 0 ? true : false;
//            if (!userExists){
//                //Refreshes the data
//                try {
//                    Response<User> response = mUserWebAPI.getUser(userId).execute();
//
//                    //handle errors here
//                    if (!response.isSuccessful()){
//
//                    }
//                    else if (response.isSuccessful()){
//                        //Updates the database. The LiveData object automatically
//                        //refreshes, so we don't need to do anything else here.
//                        response.body().setLastUpdate(new Date());
//                        mUserDAO.insertUser(response.body());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }

//    public LiveData<User> getWebUser(String user_id){
//        Call<User> call = mUserWebAPI.getUser(user_id);
//        final MutableLiveData<User> userData = new MutableLiveData<>();
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()){
//                    userData.postValue(response.body());
//                    Log.d("Chaiwa", "User REST API Call Success:" + response.body());
//                }
//                else {
//                    Log.d("Chaiwa", "User REST API Call Error" + new Gson().toJson(response.errorBody()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.d("Chaiwa", t.toString());
//
//            }
//        });
//
//        return userData;
//    }
    public Call<ResponseBody> getApiAuthToken(){
        Call<ResponseBody> call = mUserWebAPI.getAuthToken();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("Chaiwa", "Success: " + new Gson().toJson(response.body()));
                }
                else {
                    Log.d("Chaiwa", "Error: " + new Gson().toJson(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Chaiwa", t.toString());

            }
        });
        return call;
    }
    public void updateUser(User user){
        new UpdateUserAsyncTask(mUserDAO).execute(user);

    }
    public void deleteUser(User user){
        new DeleteUserAsyncTask(mUserDAO).execute(user);

    }
    public void deleteAllUsers(){
        new DeleteAllUsersAsyncTask(mUserDAO).execute();
    }
    //endregion
//region USER API ASYNC TASKS
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;
        private InsertUserAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.insertUser(users[0]);
            return null;
        }
    }
    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;
        private UpdateUserAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.updateUser(users[0]);
            return null;
        }
    }
    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;
        private DeleteUserAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.deleteUser(users[0]);
            return null;
        }
    }
    private static class DeleteAllUsersAsyncTask extends AsyncTask<Void, Void, Void>{
        private UserDAO userDAO;
        private DeleteAllUsersAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            userDAO.deleteAllUsers();
            return null;
        }
    }
//endregion
}

