package com.example.fooddeliveryapp.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class Cartmodel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "item_pic")
    public int item_pic;

    @ColumnInfo(name = "item_name")
    public String item_name;

    @ColumnInfo(name = "price")
    public String price;
}
