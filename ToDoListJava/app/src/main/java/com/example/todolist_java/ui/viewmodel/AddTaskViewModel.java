package com.example.todolist_java.ui.viewmodel;

import androidx.annotation.VisibleForTesting;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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


    @VisibleForTesting
    private MutableLiveData<String> _liveData = new MutableLiveData<>();

   // @VisibleForTesting
    public LiveData<String> liveData1 = _liveData;



    @VisibleForTesting
    public LiveData<String> liveData2 = Transformations.map(_liveData, new Function<String, String>() {
        @Override
        public String apply(String it) {
            return it.toUpperCase();
        }
    });

    @VisibleForTesting
    public void setNewValue(String value){
        _liveData.setValue(value);
    }

}
