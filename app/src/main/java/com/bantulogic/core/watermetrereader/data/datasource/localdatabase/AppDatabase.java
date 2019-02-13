package com.bantulogic.core.watermetrereader.data.datasource.localdatabase;
import android.content.Context;
import android.os.AsyncTask;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.AuthorizationDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.CustomerDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.MetreAccountDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.MetreReadingDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.UserDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Customer;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.MetreAccount;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.MetreReading;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.User;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {User.class, MetreAccount.class, MetreReading.class, Customer.class, Authorization.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDAO userDAO();
    public abstract MetreAccountDAO metreAccountDAO();
    public abstract MetreReadingDAO metreReadingDAO();
    public abstract CustomerDAO customerDAO();
    public abstract AuthorizationDAO mAuthorizationDAO();

    public static AppDatabase getDatabse(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), AppDatabase.class, "metre_reader_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new populateDbAsync(INSTANCE).execute();
                }

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    //Setup the DB with some test data
                    //When the MetreReaderApp is in production we will initialize this with the data from the
                    //Backend via REST API
                    new initializeDbAsync(INSTANCE).execute();
                }
            };

    private static class populateDbAsync extends AsyncTask<Void, Void, Void> {
        private final UserDAO mUserDAO;
        private final MetreReadingDAO mMetreReadingDAO;
        private final MetreAccountDAO mMetreAccountDAO;
        private final CustomerDAO mCustomerDAO;
        private final AuthorizationDAO mAuthorizationDAO;

        public populateDbAsync(AppDatabase dbInstance) {
            mUserDAO = dbInstance.userDAO();
            mMetreReadingDAO = dbInstance.metreReadingDAO();
            mMetreAccountDAO = dbInstance.metreAccountDAO();
            mCustomerDAO = dbInstance.customerDAO();
            mAuthorizationDAO = dbInstance.mAuthorizationDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Do the inserts from the REST API or from mocks
            return null;
        }
    }

    private static class initializeDbAsync extends AsyncTask<Void, Void, Void> {
        private final UserDAO mUserDAO;
        private final MetreReadingDAO mMetreReadingDAO;
        private final MetreAccountDAO mMetreAccountDAO;
        private final CustomerDAO mCustomerDAO;
        private final AuthorizationDAO mAuthorizationDAO;

        @Override
        protected Void doInBackground(Void... voids) {
            //Do the inserts from the REST API or from mocks
            return null;
        }


        private initializeDbAsync(AppDatabase dbInstance){
            mCustomerDAO = dbInstance.customerDAO();
            mUserDAO = dbInstance.userDAO();
            mMetreReadingDAO = dbInstance.metreReadingDAO();
            mMetreAccountDAO = dbInstance.metreAccountDAO();
            mAuthorizationDAO = dbInstance.mAuthorizationDAO();

        }
    }

    @NonNull
    @Override
    public InvalidationTracker getInvalidationTracker() {
        return super.getInvalidationTracker();
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

}
