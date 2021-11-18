package com.example.mvvm.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvm.SubjectsRepository;

import java.util.List;

public class SubjectsViewModel extends AndroidViewModel {
    private SubjectsRepository repository;
    private LiveData<List<Subjects>> allSubjects;

    public SubjectsViewModel(@NonNull Application application) {
        super(application);
        repository = new SubjectsRepository(application);
        allSubjects = repository.getAllNotes();
    }

    public void insert(Subjects subjects ) {
        repository.insert(subjects);
    }

    public void update(Subjects subjects ) {
        repository.update(subjects);
    }

    public void delete(Subjects subjects ) {
        repository.delete(subjects);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Subjects>> getAllSubjects() {
        return allSubjects;
    }
}
