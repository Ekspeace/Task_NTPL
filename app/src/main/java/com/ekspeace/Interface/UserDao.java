package com.ekspeace.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ekspeace.Model.Transaction;
import com.ekspeace.Model.UserAccount;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void Insert(UserAccount userAccount);

    @Update
    void Update(UserAccount userAccount);

    @Delete
    void Delete(UserAccount userAccount);

    @Query("SELECT * FROM user_table")
    LiveData<List<UserAccount>> getAllUserAccount();

    @Query("UPDATE user_table SET Balance = :balance WHERE AccountNumber =:id")
    void UpdateBalance(String balance, String id);

    @Query("UPDATE user_table SET Date = :date WHERE AccountNumber =:id")
    void UpdateDate(String date, String id);
}
