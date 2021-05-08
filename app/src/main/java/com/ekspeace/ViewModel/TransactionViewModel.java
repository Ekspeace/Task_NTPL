package com.ekspeace.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ekspeace.Model.Transaction;
import com.ekspeace.Repository.TransactionRepository;


import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repository;
    private LiveData<List<Transaction>> allTransactions;
    public TransactionViewModel(Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransactions = repository.getAllTransactions();
    }
    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }
    public void delete(Transaction transaction) {
        repository.delete(transaction);
    }
    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }
}
