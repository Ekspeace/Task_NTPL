package com.ekspeace.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.ekspeace.Adapter.RecycleViewUserTransaction;
import com.ekspeace.Model.Transaction;
import com.ekspeace.Model.UserAccount;
import com.ekspeace.R;
import com.ekspeace.ViewModel.TransactionViewModel;
import com.ekspeace.ViewModel.UserViewModel;

import java.util.List;


import static com.ekspeace.Model.Constants.Rand;
import static com.ekspeace.View.LoginActivity.UserAccountNumber;

public class ViewBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_balance);

        RecyclerView recyclerView = findViewById(R.id.recycleView_transaction);
        TextView balance = findViewById(R.id.user_balance_display);

        UserViewModel userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel.class);
        TransactionViewModel transactionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TransactionViewModel.class);

        SetUserBalance(balance,userViewModel);
        PopulateRecyclerView(transactionViewModel, recyclerView);
    }

    private void SetUserBalance(TextView balance, UserViewModel userViewModel) {
        userViewModel.getAllUserAccounts().observe(this, (Observer<List<UserAccount>>) userAccounts -> {
            for (UserAccount user : userAccounts) {
                if (user.getAccountNumber().equals(UserAccountNumber)) {
                    if(user.getBalance() == null){
                       balance.setText((Rand + "0.0"));
                    }else {
                        balance.setText((Rand + user.getBalance()).toString());
                    }
                }
            }
        });
    }

    private void PopulateRecyclerView(TransactionViewModel transactionViewModel, RecyclerView recyclerView){
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionViewModel.getAllTransactions().observe(this, (Observer<List<Transaction>>) transactions -> {
            RecycleViewUserTransaction adapter = new RecycleViewUserTransaction(this,transactions);
            recyclerView.setAdapter(adapter);
        });
    }
}