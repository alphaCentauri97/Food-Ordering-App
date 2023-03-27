package com.example.fooddeliveryapp.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fooddeliveryapp.Users;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM cart_table")
    List<Cartmodel> getAllItems();

    @Insert
    void insert(Cartmodel...cartmodels);

    @Delete
    void delete(Cartmodel cartmodel);
}
