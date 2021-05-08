package com.ekspeace.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.math.BigDecimal;

@Entity(tableName = "admin_transaction_request_table")
public class AdminTransactionRequest {

    private String TransactionType;
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int Key_Id;
    private String AccountNumber;
    private String ClientName;
    private String Amount;

    public AdminTransactionRequest() {
    }

    public AdminTransactionRequest(String clientName, String transactionType, String accountNumber, String amount) {
        ClientName = clientName;
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

    public String getClientName() {
        return ClientName;
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

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setTransactionType(String transactionType) { TransactionType = transactionType; }
}
