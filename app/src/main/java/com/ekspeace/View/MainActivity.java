package com.ekspeace.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ekspeace.Model.UserAccount;
import com.ekspeace.R;
import com.ekspeace.ViewModel.UserViewModel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.ekspeace.Model.Constants.formatter;
import static com.ekspeace.View.LoginActivity.UserAccountNumber;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = findViewById(R.id.username);
        String AccountNumber = getIntent().getStringExtra("ACCOUNT_NUMBER");
        String Balance = getIntent().getStringExtra("BALANCE");
        String Date = getIntent().getStringExtra("DATE");
        UserViewModel userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel.class);

        findViewById(R.id.deposit_withdrawal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DepositWithdrawalActivity.class));
            }
        });
        findViewById(R.id.view_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewBalanceActivity.class));
            }
        });

        SetUserName(username, userViewModel);
       AddInterest(userViewModel, AccountNumber, Date, Balance);
    }
    private void SetUserName(TextView username, UserViewModel userViewModel){
        userViewModel.getAllUserAccounts().observe(this, (Observer<List<UserAccount>>) userAccounts -> {
            for (UserAccount user : userAccounts){
                if (user.getAccountNumber().equals(UserAccountNumber)){
                    username.setText(user.getUserName());
                }
            }
        });
    }
    private void AddInterest(UserViewModel userViewModel, String accountNumber, String old_date, String balance) {
        Date date = new Date();
        String new_date = formatter.format(date);

        Date oldDate = null;
        Date newDate = null;
        try {
            newDate = formatter.parse(new_date);
            oldDate = formatter.parse(old_date);
        } catch (ParseException e) {
            Toast.makeText(MainActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        long differentInTime = newDate.getTime() - oldDate.getTime();
        long differentInDays = (differentInTime /(1000 * 60 * 60 * 24)) % 365;

        if (balance == null) {
            return;
        }
        BigDecimal b1 = new BigDecimal(balance);
        BigDecimal b2 = new BigDecimal("0.0");
        if (b1.compareTo(b2) < 0 || b1.compareTo(b2) == 0){
            return;
        }

        if (differentInDays >= 7) {
            BigDecimal Balance = new BigDecimal(balance);
            BigDecimal percentage = new BigDecimal("0.012");

            BigDecimal interest = Balance.multiply(percentage);
            BigDecimal total = Balance.add(interest);
            total = total.setScale(2, BigDecimal.ROUND_HALF_UP);

            userViewModel.updateBalance(total.toString(), accountNumber);
            userViewModel.updateDate(new_date, accountNumber);

        }
    }
}