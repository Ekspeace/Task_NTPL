package com.ekspeace.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ekspeace.Interface.TransactionDao;
import com.ekspeace.Model.Transaction;
import com.ekspeace.RoomDatabase.UserDatabase;

import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;

    public TransactionRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        transactionDao = database.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
    }

    public void insert(Transaction transaction) {
        new TransactionRepository.InsertTransactionAsyncTask(transactionDao).execute(transaction);
    }
    public void delete(Transaction transaction) {
        new TransactionRepository.DeleteTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    private static class InsertTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private InsertTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.Insert(transactions[0]);
            return null;
        }
    }

    private static class DeleteTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private DeleteTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.Delete(transactions[0]);
            return null;
        }
    }
}
