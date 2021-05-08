package com.ekspeace.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ekspeace.Model.UserAccount;
import com.ekspeace.Repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<UserAccount>> allUserAccounts;
    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUserAccounts = repository.getAllUserAccounts();
    }
    public void insert(UserAccount userAccount) {
        repository.insert(userAccount);
    }

    public void update(UserAccount userAccount) {
        repository.update(userAccount);
    }
    public void delete(UserAccount userAccount) {
        repository.delete(userAccount);
    }

    public void updateBalance(String balance, String id){
        repository.updateBalance(balance, id);
    }

    public void updateDate(String date, String id){
        repository.updateDate(date, id);
    }
    public LiveData<List<UserAccount>> getAllUserAccounts() {
        return allUserAccounts;
    }
}
