package com.example.mvvm;


import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm.model.Subjects;

import java.util.List;

@Dao
public interface SubjectsDao {

    @Insert
    void insert(Subjects subjects);

    @Update
    void update(Subjects subjects);

    @Delete
    void delete(Subjects subjects);

    @Query("DELETE FROM subjects")
    void deleteAllNotes();

    @Query("SELECT * FROM subjects ORDER BY id DESC")
    LiveData<List<Subjects>> getAllNotes();

}
