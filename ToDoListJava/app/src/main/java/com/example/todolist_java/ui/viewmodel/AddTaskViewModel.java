package com.example.todolist_java.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist_java.database.AppDatabase;
import com.example.todolist_java.database.entity.TaskEntry;

public class AddTaskViewModel extends ViewModel {
    private LiveData<TaskEntry> mTask;

    public AddTaskViewModel(AppDatabase mDb, int mTaskId){
        mTask = mDb.taskDao().loadTaskById(mTaskId);
    }

    public LiveData<TaskEntry> getTaskEntry(){
        return mTask;
    }
}
