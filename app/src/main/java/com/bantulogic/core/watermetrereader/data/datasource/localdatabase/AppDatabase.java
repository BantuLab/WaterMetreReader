package com.bantulogic.core.watermetrereader.data.datasource.localdatabase;
import android.content.Context;
import android.os.AsyncTask;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.AuthorizationDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.CustomerDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.FcmTokenDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.MetreAccountDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.MetreReadingDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.UserDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Customer;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.FcmToken;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.MetreAccount;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.MetreReading;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.User;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {User.class, MetreAccount.class, MetreReading.class, Customer.class, Authorization.class, FcmToken.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDAO userDAO();
    public abstract MetreAccountDAO metreAccountDAO();
    public abstract MetreReadingDAO metreReadingDAO();
    public abstract CustomerDAO customerDAO();
    public abstract AuthorizationDAO mAuthorizationDAO();
    public abstract FcmTokenDAO fcmTokenDAO();

    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), AppDatabase.class, "metre_reader_db")
                            .addCallback(sRoomDatabaseCallback)
                            .addMigrations(MIGRATION_1_2)
                            .fallbackToDestructiveMigration()
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
                }

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                }
            };

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
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //Migration when a column is removed
            //Step1: Create new temp table
            database.execSQL(
                    "CREATE TABLE Authorization_New(" +
                            "username TEXT NOT NULL, " +
                            "password TEXT NOT NULL, " +
                            "user_id TEXT NOT NULL, " +
                            "token TEXT, " +
                            "user_type TEXT, " +
                            "scope TEXT, " +
                            "iat INTEGER, " +
                            "exp INTEGER, " +
                            "aud TEXT, " +
                            "iss TEXT, " +
                            "logged_in INTEGER NOT NULL, " +
                            "PRIMARY KEY(username))"
            );
            //Step2: Copy Data
            database.execSQL("INSERT INTO Authorization_New(" +
                    "username," +
                    "password," +
                    "user_id," +
                    "token," +
                    "user_type," +
                    "scope," +
                    "iat," +
                    "exp," +
                    "aud," +
                    "iss," +
                    "logged_in)" +
                    "SELECT " +
                    "username," +
                    "password," +
                    "user_id," +
                    "token," +
                    "user_type," +
                    "scope," +
                    "iat," +
                    "exp," +
                    "aud," +
                    "iss," +
                    "logged_in FROM Authorization");
            //Step3: Remove the old data
            database.execSQL("DROP TABLE Authorization");
            //Step4: Rename temp table
            database.execSQL("ALTER TABLE Authorization_New RENAME TO Authorization");
        }
    };
}