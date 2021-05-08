package com.ekspeace.Repository;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.ekspeace.Interface.UserDao;
import com.ekspeace.Model.Transaction;
import com.ekspeace.Model.UserAccount;
import com.ekspeace.RoomDatabase.UserDatabase;

import java.util.List;


public class UserRepository {
    private UserDao userDao;
    private LiveData<List<UserAccount>> allUserAccounts;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUserAccounts = userDao.getAllUserAccount();
    }

    public void insert(UserAccount userAccount) {
        new InsertUserAsyncTask(userDao).execute(userAccount);
    }

    public void update(UserAccount userAccount) {
        new UpdateUserAsyncTask(userDao).execute(userAccount);
    }

    public void updateBalance(String balance, String id) {
        new UpdateBalanceUserAsyncTask(userDao, balance,id).execute();
    }
    public void updateDate(String date, String id) {
        new UpdateDateUserAsyncTask(userDao, date,id).execute();
    }

    public void delete(UserAccount userAccount) {
        new DeleteUserAsyncTask(userDao).execute(userAccount);
    }

    public LiveData<List<UserAccount>> getAllUserAccounts(){
        return allUserAccounts;
    }


    private static class InsertUserAsyncTask extends AsyncTask<UserAccount, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserAccount... userAccounts) {
            userDao.Insert(userAccounts[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<UserAccount, Void, Void> {
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserAccount... userAccounts) {
            userDao.Update(userAccounts[0]);
            return null;
        }
    }
        private static class DeleteUserAsyncTask extends AsyncTask<UserAccount, Void, Void> {
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserAccount... userAccounts) {
            userDao.Delete(userAccounts[0]);
            return null;
        }
    }
    private static class UpdateBalanceUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private String balance, id;

        private UpdateBalanceUserAsyncTask(UserDao userDao, String balance, String id) {
            this.userDao = userDao;
            this.balance = balance;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.UpdateBalance(balance,id);
            return null;
        }
    }

    private static class UpdateDateUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private String date, id;

        private UpdateDateUserAsyncTask(UserDao userDao, String date, String id) {
            this.userDao = userDao;
            this.date = date;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.UpdateDate(date,id);
            return null;
        }
    }
}