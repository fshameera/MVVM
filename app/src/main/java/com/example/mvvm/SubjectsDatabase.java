package com.example.mvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mvvm.model.Subjects;

@Database(entities = {Subjects.class}, version = 1)
public abstract class SubjectsDatabase extends RoomDatabase {

    private static SubjectsDatabase instance;

    public abstract SubjectsDao subjectsDao();

    public static synchronized SubjectsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SubjectsDatabase.class, "subjects_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SubjectsDao subjectsDao;

        private PopulateDbAsyncTask(SubjectsDatabase db) {
            subjectsDao = db.subjectsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            subjectsDao.insert(new Subjects(1, "Mathematics", "Combined maths"));
            subjectsDao.insert(new Subjects(2, "Science", "Organic chemistry"));
            subjectsDao.insert(new Subjects(3, "Arts", "Sculptures"));
            return null;
        }
    }

}
