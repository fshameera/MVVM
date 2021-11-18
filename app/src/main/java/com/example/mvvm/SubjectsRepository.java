package com.example.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mvvm.model.Subjects;

import java.util.List;

public class SubjectsRepository {
    private SubjectsDao subjectsDao;
    private LiveData<List<Subjects>> allSubjects;

    public SubjectsRepository(Application application) {
        SubjectsDatabase database = SubjectsDatabase.getInstance(application);
        subjectsDao = database.subjectsDao();
        allSubjects = subjectsDao.getAllNotes();
    }

    public void insert(Subjects subjects) {
        new InsertNoteAsyncTask(subjectsDao).execute(subjects);
    }

    public void update(Subjects subjects) {
        new UpdateNoteAsyncTask(subjectsDao).execute(subjects);
    }

    public void delete(Subjects subjects) {
        new DeleteNoteAsyncTask(subjectsDao).execute(subjects);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(subjectsDao).execute();
    }

    public LiveData<List<Subjects>> getAllNotes() {
        return allSubjects;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Subjects, Void, Void> {
        private SubjectsDao subjectsDao;

        private InsertNoteAsyncTask(SubjectsDao subjectsDao) {
            this.subjectsDao = subjectsDao;
        }

        @Override
        protected Void doInBackground(Subjects... subjects) {
            subjectsDao.insert(subjects[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Subjects, Void, Void> {
        private SubjectsDao subjectsDao;

        private UpdateNoteAsyncTask(SubjectsDao subjectsDao) {
            this.subjectsDao = subjectsDao;
        }

        @Override
        protected Void doInBackground(Subjects... subjects) {
            subjectsDao.update(subjects[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Subjects, Void, Void> {
        private SubjectsDao subjectsDao;

        private DeleteNoteAsyncTask(SubjectsDao subjectsDao) {
            this.subjectsDao = subjectsDao;
        }

        @Override
        protected Void doInBackground(Subjects... subjects) {
            subjectsDao.delete(subjects[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private SubjectsDao subjectsDao;

        private DeleteAllNotesAsyncTask(SubjectsDao subjectsDao) {
            this.subjectsDao = subjectsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            subjectsDao.deleteAllNotes();
            return null;
        }
    }
}
