package com.ekspeace.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ekspeace.Adapter.RecycleViewClientRequests;
import com.ekspeace.Adapter.RecycleViewUserTransaction;
import com.ekspeace.Model.AdminTransactionRequest;
import com.ekspeace.Model.Transaction;
import com.ekspeace.Model.UserAccount;
import com.ekspeace.R;
import com.ekspeace.ViewModel.AdminTransactionRequestViewModel;
import com.ekspeace.ViewModel.TransactionViewModel;
import com.ekspeace.ViewModel.UserViewModel;

import java.time.LocalDateTime;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class AdminDashboardActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        RecyclerView recyclerView = findViewById(R.id.recycleView_clients_requests);
        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel.class);
        AdminTransactionRequestViewModel adminTransactionRequestViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AdminTransactionRequestViewModel.class);

        userViewModel.getAllUserAccounts().observe(this, (Observer<List<UserAccount>>) userAccounts -> {
        });
        PopulateRecyclerView(adminTransactionRequestViewModel, recyclerView);
    }

    private void PopulateRecyclerView(AdminTransactionRequestViewModel adminTransactionRequestViewModel, RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminTransactionRequestViewModel.getAllAdminTransactions().observe(this, (Observer<List<AdminTransactionRequest>>) adminTransactionRequests -> {
            RecycleViewClientRequests adapter = new RecycleViewClientRequests(this, adminTransactionRequests);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new RecycleViewClientRequests.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String AccountNumber = adminTransactionRequests.get(position).getAccountNumber();
                    int KeyId = adminTransactionRequests.get(position).getKey_Id();
                    Intent intent = new Intent(getBaseContext(), AdminRequestConfirmationActivity.class);
                    intent.putExtra("KEY_ID", KeyId);
                    intent.putExtra("ACCOUNT_NUMBER", AccountNumber);
                    startActivity(intent);
                }
            });
        });

    }
}