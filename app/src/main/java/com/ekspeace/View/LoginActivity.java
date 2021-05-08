package com.ekspeace.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ekspeace.Model.UserAccount;
import com.ekspeace.R;
import com.ekspeace.ViewModel.UserViewModel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.ekspeace.Model.Constants.SwitchUser;
import static com.ekspeace.Model.Constants.formatter;

public class LoginActivity extends AppCompatActivity {

    public static String UserAccountNumber;
    private UserViewModel userViewModel;
    private EditText user_name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_name = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel.class);
        userViewModel.getAllUserAccounts().observe(this, (Observer<List<UserAccount>>) userAccounts -> {
        });

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserVerification();
            }
        });
        findViewById(R.id.User_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        findViewById(R.id.text_user_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_user).setVisibility(View.VISIBLE);
                findViewById(R.id.login_admin).setVisibility(View.GONE);
                SwitchUser = true;
            }
        });
        findViewById(R.id.text_admin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_user).setVisibility(View.GONE);
                findViewById(R.id.login_admin).setVisibility(View.VISIBLE);
                SwitchUser = false;
            }
        });

        ChangeAuthority();
    }
    private void ChangeAuthority(){

        if (SwitchUser){
            findViewById(R.id.login_user).setVisibility(View.VISIBLE);
            findViewById(R.id.login_admin).setVisibility(View.GONE);
        }else {
            findViewById(R.id.login_user).setVisibility(View.GONE);
            findViewById(R.id.login_admin).setVisibility(View.VISIBLE);
        }

    }
    private void UserVerification() {
        String UserName = user_name.getText().toString().trim();
        String Password = password.getText().toString();

        boolean error = false;
        if (!error) {
            if (TextUtils.isEmpty(UserName)) {
                user_name.setError("UserName is required");
                error = true;
            }
            if (TextUtils.isEmpty(Password)) {
                password.setError("Password is Required.");
                error = true;
            }
            if (error)
                return;
        }

        if (SwitchUser) {
            for (UserAccount user : userViewModel.getAllUserAccounts().getValue()) {
                    if (user.getUserName().equals(UserName) && user.getPassword().equals(Password)) {
                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        UserAccountNumber = user.getAccountNumber();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("DATE", user.getDate());
                        intent.putExtra("ACCOUNT_NUMBER", user.getAccountNumber());
                        intent.putExtra("BALANCE", user.getBalance());
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            if (userViewModel.getAllUserAccounts().getValue().isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please add account", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (UserName.equals("Admin") && Password.equals("123456")){
                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed, please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

}