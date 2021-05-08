package com.ekspeace.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ekspeace.Model.UserAccount;
import com.ekspeace.R;
import com.ekspeace.ViewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ekspeace.Model.Constants.formatter;

public class RegisterActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private EditText user_name, mobile_number, email, account_number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_name = findViewById(R.id.user_name);
        mobile_number = findViewById(R.id.user_mobile_number);
        email = findViewById(R.id.user_email);
        account_number = findViewById(R.id.user_account_number);
        password = findViewById(R.id.user_password);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserViewModel.class);

        findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUserAccount();
            }
        });
        findViewById(R.id.User_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void SaveUserAccount() {

        String UserName = user_name.getText().toString().trim();
        String MobileNumber = mobile_number.getText().toString().trim();
        String Email = email.getText().toString();
        String AccountNumber = account_number.getText().toString();
        String Password = password.getText().toString();

        boolean error = false;
        if (!error) {
            if (TextUtils.isEmpty(Email)) {
                email.setError("Email is Required.");
                error = true;
            }
            if (TextUtils.isEmpty(UserName)) {
                user_name.setError("UserName is required");
                error = true;
            }
            if (TextUtils.isEmpty(Password)) {
                password.setError("Password is Required.");
                error = true;
            }
            if (TextUtils.isEmpty(MobileNumber)) {
                mobile_number.setError("Phone is Required.");
                error = true;
            }
            if (TextUtils.isEmpty(AccountNumber)) {
                account_number.setError("Address is Required.");
                error = true;
            }
            if (error)
                return;
        }
        Date date = new Date();
        UserAccount userAccount = new UserAccount(UserName,MobileNumber,Email,Password,AccountNumber, formatter.format(date));
        userViewModel.insert(userAccount);

        Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}