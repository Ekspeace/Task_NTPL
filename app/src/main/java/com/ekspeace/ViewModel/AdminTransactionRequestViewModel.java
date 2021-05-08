package com.ekspeace.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ekspeace.Model.AdminTransactionRequest;
import com.ekspeace.Repository.AdminTransactionRequestRepository;

import java.util.List;

public class AdminTransactionRequestViewModel extends AndroidViewModel {

    private AdminTransactionRequestRepository repository;
    private LiveData<List<AdminTransactionRequest>> allAdminTransactions;
    public AdminTransactionRequestViewModel(Application application) {
        super(application);
        repository = new AdminTransactionRequestRepository(application);
        allAdminTransactions = repository.getAllTransactions();
    }
    public void insert(AdminTransactionRequest adminTransactionRequest) {
        repository.insert(adminTransactionRequest);
    }
    public void delete(AdminTransactionRequest adminTransactionRequest) {
        repository.delete(adminTransactionRequest);
    }
    public LiveData<List<AdminTransactionRequest>> getAllAdminTransactions() {
        return allAdminTransactions;
    }
    public void deleteById(int KeyId){
        repository.deleteById(KeyId);
    }
}
