package com.example.todolist_java.ui;

import android.content.Intent;
import android.database.Observable;

import com.example.todolist_java.database.entity.TaskEntry;
import com.example.todolist_java.ui.viewmodel.AddTaskViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AddTaskActivityTest {

    private AddTaskActivity addTaskActivity;
    private Intent intent;
    private AddTaskActivity spyActivity;


    AddTaskViewModel addTaskViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
//TestObser

        addTaskActivity = Robolectric.buildActivity(AddTaskActivity.class, intent).create().get();
        spyActivity = spy(addTaskActivity);

        Observable<TaskEntry> observable = (Observable<TaskEntry>) mock(Observable.class);

//        when(observable.subscribeOn(Schedulers.io())).thenReturn(observable);
//        when(observable.observeOn(AndroidSchedulers.mainThread())).thenReturn(observable);


    }


    @Test
    public void testLiveData() {
    }
}