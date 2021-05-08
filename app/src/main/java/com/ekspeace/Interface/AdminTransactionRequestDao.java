package com.ekspeace.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ekspeace.Model.AdminTransactionRequest;

import java.util.List;

@Dao
public interface AdminTransactionRequestDao {

    @Insert
    void Insert(AdminTransactionRequest adminTransactionRequest);

    @Delete
    void Delete(AdminTransactionRequest adminTransactionRequest);

    @Query("SELECT * FROM admin_transaction_request_table")
    LiveData<List<AdminTransactionRequest>> getAllAdminTransactions();

    @Query("DELETE FROM admin_transaction_request_table WHERE Key_Id = :KeyId")
    void DeleteById(int KeyId);

}
