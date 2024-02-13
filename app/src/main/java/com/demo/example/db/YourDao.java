package com.demo.example.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.demo.example.model.YourDataModel;

import java.util.List;

@Dao
public interface YourDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<YourDataModel> dataList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<YourDataModel> data);

    @Query("SELECT * FROM YourDataModel")
    LiveData<List<YourDataModel>> getAllData();

    @Query("SELECT * FROM YourDataModel")
    List<YourDataModel> getAllData2();

    @Query("SELECT COUNT(*) FROM YourDataModel")
    int getDataCount();

}