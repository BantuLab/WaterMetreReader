package com.bantulogic.core.watermetrereader.Data.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO.UserDAO;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.User;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.ServiceGenerator;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.UserWebAPI;
import com.google.gson.Gson;

import java.util.List;
import androidx.lifecycle.LiveData;
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
 */
public class UserRepository {
    private UserDAO mUserDAO;
    private UserWebAPI mUserWebAPI;

    private LiveData<List<User>> mAllUsers;
    private LiveData<User> mUser;

    public UserRepository(Application application){
        AppDatabase db  = AppDatabase.getDatabse(application);

        mUserDAO = db.userDAO();
        mUserWebAPI = ServiceGenerator.createService(UserWebAPI.class,"peter@klaven","cityslicka");

        mAllUsers = mUserDAO.getAllUsers();
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

    public LiveData<User> getUserById(String userId){
        //TODO Fix the User Retrieval Workflow
        //Here we can check if logged-in, then we return the logged-in user. At login we check if
        //there is network we try to get the user from the REST API then cache the user to local DB,
        //so if requesting the user and offline then authenticate using local cached user/profile
        //otherwise query the user from the network REST API

        mUser = mUserDAO.getUserById(userId);
        return mUser;
    }

    public LiveData<User> getCurrentUser() {
        return mUser;
    }
    public Call<User> getWebUser(int user_id){
        Call<User> call = mUserWebAPI.getUser(user_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.d("Chaiwa", "Success:" + response.body());
                }
                else {
                    Log.d("Chaiwa", "Error" + new Gson().toJson(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Chaiwa", t.toString());

            }
        });

        return call;
    }
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
    public  void insertUser(User user){
        new InsertUserAsyncTask(mUserDAO).execute(user);
    }
    public void updateUser(User user){
        new UpdateUserAsyncTask(mUserDAO).execute(user);

    }
    public void deleteUser(User user){
        new DeleteUserAsyncTask(mUserDAO).execute(user);

    }
    public void insertManyUsers(User... users){
        new InsertManyUsersAsyncTask(mUserDAO).execute(users);
    }

    public void updateManyUsers(User... users){
        new UpdateManyUsersAsyncTask(mUserDAO).execute(users);
    }
    public void deleteAllUsers(){
        new DeleteAllUsersAsyncTask(mUserDAO).execute();
    }
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
    private static class InsertManyUsersAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;
        private InsertManyUsersAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.insertManyUsers(users);
            return null;
        }
    }
    private static class UpdateManyUsersAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDAO userDAO;
        private UpdateManyUsersAsyncTask(UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.updateManyUsers(users);
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

