package com.ekspeace.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.TransitionRes;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.ekspeace.Interface.AdminTransactionRequestDao;
import com.ekspeace.Interface.TransactionDao;
import com.ekspeace.Interface.UserDao;
import com.ekspeace.Model.AdminTransactionRequest;
import com.ekspeace.Model.Transaction;
import com.ekspeace.Model.UserAccount;

@Database(entities = {UserAccount.class, Transaction.class, AdminTransactionRequest.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao userDao();

    public abstract TransactionDao transactionDao();

    public abstract AdminTransactionRequestDao adminTransactionRequestDao();

    public static synchronized UserDatabase getInstance(Context context)
    {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
