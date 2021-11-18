package com.example.mvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm.adapter.SubjectsAdapter;
import com.example.mvvm.model.Subjects;
import com.example.mvvm.model.SubjectsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SubjectsViewModel subjectsViewModel;
    public static final int ADD_SUBJECT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.floatingActionButton);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSubjects.class);
                startActivityForResult(intent, ADD_SUBJECT_REQUEST);
            }
        });




        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SubjectsAdapter adapter = new SubjectsAdapter();
        recyclerView.setAdapter(adapter);


        subjectsViewModel = ViewModelProviders.of(this).get(SubjectsViewModel.class);
        subjectsViewModel.getAllSubjects().observe(this, new Observer<List<Subjects>>() {
            @Override
            public void onChanged(@Nullable List<Subjects> subjects) {
                adapter.setSubjects(subjects);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                subjectsViewModel.delete(adapter.getSubjectAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SUBJECT_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddSubjects.EXTRA_TITLE);
            String description = data.getStringExtra(AddSubjects.EXTRA_DESCRIPTION);
            int id = data.getIntExtra(AddSubjects.EXTRA_ID, 1);

            Subjects note = new Subjects(id, title, description);
            subjectsViewModel.insert(note);

            Toast.makeText(this, "Subjects saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Subjects not saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                subjectsViewModel.deleteAllNotes();
                Toast.makeText(this, "All subjects deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}