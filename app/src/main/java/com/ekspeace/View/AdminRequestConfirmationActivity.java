package com.ekspeace.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ekspeace.Model.AdminTransactionRequest;
import com.ekspeace.Model.Transaction;
import com.ekspeace.Model.UserAccount;
import com.ekspeace.R;
import com.ekspeace.ViewModel.AdminTransactionRequestViewModel;
import com.ekspeace.ViewModel.TransactionViewModel;
import com.ekspeace.ViewModel.UserViewModel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.util.List;

import static com.ekspeace.Model.Constants.Rand;
import static com.ekspeace.Model.Constants.SwitchUser;
import static com.ekspeace.View.LoginActivity.UserAccountNumber;

public class AdminRequestConfirmationActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private AdminTransactionRequestViewModel adminTransactionRequestViewModel;
    private int KeyId;
    private Button deposit, withdraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request_confirmation);

        KeyId = getIntent().getIntExtra("KEY_ID",0);
        deposit =  findViewById(R.id.btn_deposit);
        withdraw =  findViewById(R.id.btn_withdraw);

        TextView client_name = findViewById(R.id.client_name);
        TextView client_account_number =  findViewById(R.id.client_account_number);
        TextView client_transaction_type =  findViewById(R.id.client_transaction_type);
        TextView client_amount = findViewById(R.id.client_amount);
        adminTransactionRequestViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AdminTransactionRequestViewModel.class);
        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel.class);

        adminTransactionRequestViewModel.getAllAdminTransactions().observe(this, (Observer<List<AdminTransactionRequest>>) adminTransactionRequests -> {
            for (AdminTransactionRequest adminTransactionRequest : adminTransactionRequests){
                if (adminTransactionRequest.getKey_Id() == KeyId){
                    client_name.setText(adminTransactionRequest.getClientName());
                    client_account_number.setText(adminTransactionRequest.getAccountNumber());
                    client_transaction_type.setText(adminTransactionRequest.getTransactionType());
                    client_amount.setText((Rand + adminTransactionRequest.getAmount()).toString());

                    if(adminTransactionRequest.getTransactionType().contains("D")){
                        deposit.setVisibility(View.VISIBLE);
                    }else{
                        withdraw.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        userViewModel.getAllUserAccounts().observe(this, (Observer<List<UserAccount>>) userAccounts -> {});
        ConfirmDepositWithdrawalRequest();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DialogBox(String title, String message, String transactionType, String amount){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminRequestConfirmationActivity.this);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    UpdateUserAmount(amount, transactionType);
                    DeleteRequests();
                    finish();
                    Toast.makeText(getApplicationContext(),transactionType + " Confirmed",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                })
                .create()
                .show();
    }
    private void ConfirmDepositWithdrawalRequest() {

        EditText amount_entered = findViewById(R.id.enter_amount);

        deposit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Editable AmountEnter = amount_entered.getText();
                if (TextUtils.isEmpty(AmountEnter)) {
                    amount_entered.setError("Amount is required");
                    return;
                }
                String AmountEntered = amount_entered.getText().toString();
                DialogBox("Confirm Deposit", "Are you sure the amount entered is correct ?", "Deposit", AmountEntered);
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Editable AmountEnter = amount_entered.getText();
                if (TextUtils.isEmpty(AmountEnter)) {
                    amount_entered.setError("Amount is required");
                    return;
                }
                String AmountEntered = amount_entered.getText().toString();
                DialogBox("Confirm Withdrawal", "Are you sure the amount entered is correct ?", "Withdraw", AmountEntered);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void UpdateUserAmount(String balance, String transactionType) {
        String AccountNumber = getIntent().getStringExtra("ACCOUNT_NUMBER");
            for (UserAccount user : userViewModel.getAllUserAccounts().getValue()) {
                if (user.getAccountNumber().equals(AccountNumber)) {
                    BigDecimal bigDecimal = BalanceCalculation(user.getBalance(), balance, transactionType);
                    String Balance = bigDecimal.toString();
                    userViewModel.updateBalance(Balance, AccountNumber);
                }
            }

    }
    private BigDecimal BalanceCalculation(String oldValue, String newValue, String transaction_type){
        if(oldValue == null){
            oldValue = "0.0";
        }
        BigDecimal old_value = new BigDecimal(oldValue);
        BigDecimal new_value = new BigDecimal(newValue);
        if (transaction_type.contains("D")) {
            return old_value.add(new_value);
        }else {
           return old_value.subtract(new_value);
        }
    }
    private void DeleteRequests(){
            adminTransactionRequestViewModel.deleteById(KeyId);
        }
}