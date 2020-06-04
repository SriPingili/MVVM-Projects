package com.example.todolist_java.ui.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist_java.database.AppDatabase;
import com.example.todolist_java.ui.viewmodel.AddTaskViewModel;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase mDb;
    private int mTaskId;

    public AddTaskViewModelFactory(AppDatabase mDb, int mTaskId) {
        this.mDb = mDb;
        this.mTaskId = mTaskId;
    }

    // Note: This can be reused with minor modifications
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddTaskViewModel(mDb, mTaskId);
    }
}
