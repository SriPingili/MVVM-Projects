package com.example.todolist_java.ui;

import android.content.Intent;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.todolist_java.database.entity.TaskEntry;
import com.example.todolist_java.ui.viewmodel.AddTaskViewModel;

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

import java.util.Date;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class AddTaskActivityTest {

    private AddTaskActivity addTaskActivity;
    private Intent intent;
    private AddTaskActivity spyActivity;

    @Mock
    MutableLiveData<TaskEntry> liveData;

    @Mock
    AddTaskViewModel addTaskViewModel;

    @Mock
    Observer<TaskEntry> observable;

    @Captor
    ArgumentCaptor<Observer<TaskEntry>> teamsObserverCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        addTaskActivity = Robolectric.buildActivity(AddTaskActivity.class, intent).create().get();
        spyActivity = spy(addTaskActivity);

    }

    /*https://medium.com/exploring-android/android-architecture-components-testing-your-viewmodel-livedata-70177af89c6e*/
    @Test
    public void testOnChangeLiveData() {
        TaskEntry taskEntry = new TaskEntry(123, "description", 1, new Date());
        MutableLiveData<TaskEntry> liveDataTest = new MutableLiveData<>();
        liveDataTest.setValue(taskEntry);
        spyActivity.setTestViewModel(addTaskViewModel);
        liveDataTest.observeForever(observable);

        doReturn(liveDataTest).when(addTaskViewModel).getTaskEntry();


        spyActivity.testLiveData();


        verify(observable).onChanged(taskEntry);
    }

    /*
    https://medium.com/tribalscale/testing-viewmodels-and-activities-in-your-mvvm-app-5bf946559258
    * */
    @Test
    public void testLiveDataPopulateUICall() {
        TaskEntry taskEntry = new TaskEntry(123, "description", 1, new Date());
        spyActivity.setTestViewModel(addTaskViewModel);

        doReturn(liveData).when(addTaskViewModel).getTaskEntry();


        spyActivity.testLiveData();

        verify(liveData).observe(ArgumentMatchers.any(LifecycleOwner.class), teamsObserverCaptor.capture());


        teamsObserverCaptor.getValue().onChanged(taskEntry);
        verify(liveData).removeObserver(teamsObserverCaptor.getValue());
        verify(spyActivity).populateUI(taskEntry);

    }
}