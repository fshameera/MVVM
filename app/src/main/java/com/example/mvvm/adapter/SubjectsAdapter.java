package com.example.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.model.Subjects;

import java.util.ArrayList;
import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectsHolder> {
    private List<Subjects> subjects  = new ArrayList<>();

    @NonNull
    @Override
    public SubjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subjects_item, parent, false);
        return new SubjectsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsHolder holder, int position) {
        Subjects currentSubjects = subjects.get(position);
        holder.textViewTitle.setText(currentSubjects.getTitle());
        holder.textViewDescription.setText(currentSubjects.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentSubjects.getId()));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
    }
    public Subjects getSubjectAt(int position) {
        return subjects.get(position);
    }
    class SubjectsHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewPriority;

    public SubjectsHolder(View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.text_view_title);
        textViewDescription = itemView.findViewById(R.id.text_view_description);
        textViewPriority = itemView.findViewById(R.id.text_view_priority);
    }
}
}
