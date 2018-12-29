package com.bantulogic.core.watermetrereader.Data.Repository;

import android.app.Application;
import android.os.AsyncTask;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO.UserDAO;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.User;
import java.util.List;
import androidx.lifecycle.LiveData;
/*
*The UserRepository exposes the data to the UI layer.
* To ensure that the UI uses the list of MetreAccounts only after the database has been pre-populated,
 * a MediatorLiveData object is used. This observes the changes of the list of MetreAccounts and only
 * forwards it when the database is ready to be used.
 */
public class UserRepository {
    private UserDAO mUserDAO;
    private LiveData<List<User>> mAllUsers;
    private LiveData<User> mUser;

    public UserRepository(Application application){
        AppDatabase db  = AppDatabase.getDatabse(application);

        mUserDAO = db.userDAO();
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

