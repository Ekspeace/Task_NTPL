package com.ekspeace.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
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
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;

import io.paperdb.Paper;

import static com.ekspeace.Model.Constants.Rand;
import static com.ekspeace.Model.Constants.SwitchUser;
import static com.ekspeace.View.LoginActivity.UserAccountNumber;

public class DepositWithdrawalActivity extends AppCompatActivity {
    private TransactionViewModel transactionViewModel;
    private AdminTransactionRequestViewModel adminTransactionRequestViewModel;
    private String Balance;
    private String UserName;
    public static final String CHANNEL_ID = "Channel";
    public static final int NOTIFICATION_ID = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_withdrawal);

        TextView balance = findViewById(R.id.user_balance_display);
        UserViewModel userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel.class);
        transactionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TransactionViewModel.class);
        adminTransactionRequestViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AdminTransactionRequestViewModel.class);
        SetUserBalance(balance,userViewModel);
        WithdrawOrDepositCash();
    }

    private void SetUserBalance(TextView balance, UserViewModel userViewModel){
        userViewModel.getAllUserAccounts().observe(this, (Observer<List<UserAccount>>) userAccounts -> {
            for (UserAccount user : userAccounts){
                if (user.getAccountNumber().equals(UserAccountNumber)){
                    if(user.getBalance() == null){
                        balance.setText((Rand + "0.0"));
                        Balance = "0.0";
                    }else {
                        balance.setText((Rand + user.getBalance()).toString());
                        Balance = user.getBalance();
                    }
                    UserName = user.getUserName();

                }
            }
        });
    }
    private void DialogBox(String title, String message, String transactionType, String amount){
        AlertDialog.Builder builder = new AlertDialog.Builder(DepositWithdrawalActivity.this);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    AddTransaction(amount, transactionType,UserAccountNumber);
                    Notification(transactionType);
                    SwitchUser = false;
                    finish();
                    Toast.makeText(getApplicationContext(),transactionType + " request sent successfully",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                })
                .create()
                .show();
    }
    private void WithdrawOrDepositCash() {

        EditText amount_entered = findViewById(R.id.amount_entered);

        findViewById(R.id.btn_deposit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable AmountEnter = amount_entered.getText();
                if (TextUtils.isEmpty(AmountEnter)) {
                    amount_entered.setError("Amount is required");
                    return;
                }
                String AmountEntered = amount_entered.getText().toString();

                DialogBox("Deposit Money", "Are you sure you want to deposit the amount ?", "Deposit", AmountEntered);
            }
        });
        findViewById(R.id.btn_withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable AmountEnter = amount_entered.getText();
                if (TextUtils.isEmpty(AmountEnter)) {
                    amount_entered.setError("Amount is required");
                    return;
                }
                String AmountEntered = amount_entered.getText().toString();
                BigDecimal new_amount = new BigDecimal(AmountEntered);
                BigDecimal old_amount = new BigDecimal(Balance);
                int compare_value = new_amount.compareTo(old_amount);
                if (compare_value == 1){
                    Toast.makeText(DepositWithdrawalActivity.this, "Do not have enough money to withdraw", Toast.LENGTH_SHORT).show();
                    return;
                }
                DialogBox("Withdraw Money", "Are you sure you want to withdraw the amount ?", "Withdraw", AmountEntered);
            }
        });
    }
    private void Notification(String transactionType) {
        createNotificationChannel();
        Intent notifyIntent = new Intent(this, LoginActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("User " + transactionType + " Amount")
                .setContentText("Please click the notification to attend the user request")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    private void AddTransaction(String Amount, String transactionType, String UserAccountNumber){

        Transaction transaction = new Transaction(transactionType,UserAccountNumber,Amount);
        transactionViewModel.insert(transaction);

        AdminTransactionRequest adminTransactionRequest = new AdminTransactionRequest(UserName,transactionType,UserAccountNumber,Amount);
        adminTransactionRequestViewModel.insert(adminTransactionRequest);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}