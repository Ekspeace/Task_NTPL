package com.ekspeace.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ekspeace.Interface.AdminTransactionRequestDao;
import com.ekspeace.Model.AdminTransactionRequest;
import com.ekspeace.RoomDatabase.UserDatabase;

import java.util.List;

public class AdminTransactionRequestRepository {

    private AdminTransactionRequestDao adminTransactionRequestDao;
    private LiveData<List<AdminTransactionRequest>> allTransactions;

    public AdminTransactionRequestRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        adminTransactionRequestDao = database.adminTransactionRequestDao();
        allTransactions = adminTransactionRequestDao.getAllAdminTransactions();
    }

    public void insert(AdminTransactionRequest adminTransactionRequest) {
        new AdminTransactionRequestRepository.InsertAdminTransactionAsyncTask(adminTransactionRequestDao).execute(adminTransactionRequest);
    }
    public void delete(AdminTransactionRequest adminTransactionRequest) {
        new AdminTransactionRequestRepository.DeleteAdminTransactionAsyncTask(adminTransactionRequestDao).execute(adminTransactionRequest);
    }
    public void deleteById(int KeyId) {
        new AdminTransactionRequestRepository.DeleteByIdAdminTransactionAsyncTask(KeyId,adminTransactionRequestDao).execute();
    }
    public LiveData<List<AdminTransactionRequest>> getAllTransactions() {
        return allTransactions;
    }

    private static class InsertAdminTransactionAsyncTask extends AsyncTask<AdminTransactionRequest, Void, Void> {
        private AdminTransactionRequestDao adminTransactionRequestDao;

        private InsertAdminTransactionAsyncTask(AdminTransactionRequestDao adminTransactionRequestDao) {
            this.adminTransactionRequestDao = adminTransactionRequestDao;
        }

        @Override
        protected Void doInBackground(AdminTransactionRequest... adminTransactionRequests) {
            adminTransactionRequestDao.Insert(adminTransactionRequests[0]);
            return null;
        }
    }

    private static class DeleteAdminTransactionAsyncTask extends AsyncTask<AdminTransactionRequest, Void, Void> {
        private AdminTransactionRequestDao adminTransactionRequestDao;

        private DeleteAdminTransactionAsyncTask(AdminTransactionRequestDao adminTransactionRequestDao) {
            this.adminTransactionRequestDao = adminTransactionRequestDao;
        }

        @Override
        protected Void doInBackground(AdminTransactionRequest... adminTransactionRequests) {
            adminTransactionRequestDao.Delete(adminTransactionRequests[0]);
            return null;
        }
    }
    private static class DeleteByIdAdminTransactionAsyncTask extends AsyncTask<Void, Void, Void> {
        private int KeyId;
        private AdminTransactionRequestDao adminTransactionRequestDao;

        private DeleteByIdAdminTransactionAsyncTask(int KeyId, AdminTransactionRequestDao adminTransactionRequestDao) {
            this.KeyId = KeyId;
            this.adminTransactionRequestDao = adminTransactionRequestDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            adminTransactionRequestDao.DeleteById(KeyId);
            return null;
        }
    }
}
