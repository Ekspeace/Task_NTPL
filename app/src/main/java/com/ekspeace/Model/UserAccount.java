package com.ekspeace.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "user_table")
public class UserAccount {

    private String UserName, MobileNumber, EmailId, Password;
    @NonNull
    @PrimaryKey
    private String AccountNumber;

    private String Balance;

    private String Date;

    public String getUserName() {
        return UserName;
    }

    public UserAccount() {
    }

    public UserAccount(String userName, String mobileNumber, String emailId, String password, String accountNumber) {
        UserName = userName;
        MobileNumber = mobileNumber;
        EmailId = emailId;
        Password = password;
        AccountNumber = accountNumber;
    }
    public UserAccount(String userName, String mobileNumber, String emailId, String password, String accountNumber, String date) {
        UserName = userName;
        MobileNumber = mobileNumber;
        EmailId = emailId;
        Password = password;
        AccountNumber = accountNumber;
        Date = date;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }


    public String getEmailId() {
        return EmailId;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public String getPassword() {
        return Password;
    }


    public String getBalance() {
        return Balance;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
