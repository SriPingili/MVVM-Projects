package com.example.todolist_java.ui;

import android.content.Intent;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.todolist_java.database.entity.TaskEntry;
import com.example.todolist_java.recyclerview.TaskAdapter;
import com.example.todolist_java.ui.viewmodel.MainViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;
    private Intent intent;
    private MainActivity spyActivity;


    @Mock
    MainViewModel mainViewModel;

    @Mock
    Observer<List<TaskEntry>> observable;

    @Mock
    MutableLiveData<List<TaskEntry>> liveData;

    @Captor
    ArgumentCaptor<Observer<List<TaskEntry>>> teamsObserverCaptor;

    @Mock
    protected TaskAdapter adapter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mainActivity = Robolectric.buildActivity(MainActivity.class, intent).create().get();
        spyActivity = spy(mainActivity);


    }

    @Test
    public void onItemClickListener() {

        spyActivity.onItemClickListener(5);

        verify(spyActivity).startActivity(any(Intent.class));
    }


    /*https://medium.com/exploring-android/android-architecture-components-testing-your-viewmodel-livedata-70177af89c6e*/
    @Test
    public void testOnChangeLiveData() {
        ArrayList<TaskEntry> tasks = initData();

        MutableLiveData<List<TaskEntry>> liveDataTest = new MutableLiveData<>();
        liveDataTest.setValue(tasks);

        spyActivity.setTestViewModel(mainViewModel);
        liveDataTest.observeForever(observable);

        doReturn(liveDataTest).when(mainViewModel).getTasks();


        spyActivity.setupViewModel();

        verify(observable).onChanged(tasks);
    }

    /*
    https://medium.com/tribalscale/testing-viewmodels-and-activities-in-your-mvvm-app-5bf946559258
    * */
    @Test
    public void testSetUpViewModel() {
        ArrayList<TaskEntry> tasks = initData();

        spyActivity.mAdapter =adapter;

        spyActivity.setTestViewModel(mainViewModel);

        doReturn(liveData).when(mainViewModel).getTasks();

        spyActivity.setupViewModel();

        verify(liveData).observe(ArgumentMatchers.any(LifecycleOwner.class), teamsObserverCaptor.capture());

        teamsObserverCaptor.getValue().onChanged(tasks);


        verify(adapter).setTasks(tasks);
    }

    private ArrayList<TaskEntry> initData(){
        TaskEntry taskEntryOne = new TaskEntry(123, "description", 1, new Date());
        TaskEntry taskEntryTwo = new TaskEntry(123, "description", 1, new Date());
        ArrayList<TaskEntry> tasksss = new ArrayList<>();
        tasksss.add(taskEntryOne);
        tasksss.add(taskEntryTwo);

        return tasksss;
    }
}