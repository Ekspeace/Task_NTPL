package com.ekspeace.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity(tableName = "transaction_table")
public class Transaction {

    private String TransactionType;
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int Key_Id;
    private String AccountNumber;
    private String Amount;

    public Transaction() {
    }

    public Transaction(String transactionType, String accountNumber, String amount) {
        TransactionType = transactionType;
        AccountNumber = accountNumber;
        Amount = amount;
    }

    public String getTransactionType() { return TransactionType; }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public String getAmount() {
        return Amount;
    }

    public int getKey_Id() {
        return Key_Id;
    }

    public void setKey_Id(int key_Id) {
        Key_Id = key_Id;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setTransactionType(String transactionType) { TransactionType = transactionType; }

}
