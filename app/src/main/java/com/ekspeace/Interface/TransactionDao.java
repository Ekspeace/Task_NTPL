package com.ekspeace.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ekspeace.Model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void Insert(Transaction transaction);

    @Delete
    void Delete(Transaction transaction);

    @Query("SELECT * FROM transaction_table ORDER BY Key_Id DESC")
    LiveData<List<Transaction>> getAllTransactions();
}
