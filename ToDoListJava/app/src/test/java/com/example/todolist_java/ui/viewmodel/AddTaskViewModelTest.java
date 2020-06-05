package com.example.todolist_java.ui.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.test.InstrumentationRegistry;

import com.example.todolist_java.LiveDataTestUtil;
import com.example.todolist_java.database.AppDatabase;
import com.example.todolist_java.database.dao.TaskDao;
import com.example.todolist_java.database.entity.TaskEntry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.util.DataSource;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class AddTaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    AddTaskViewModel addTaskViewModel;

    @Mock
    TaskDao taskDao;

    @Mock
    AppDatabase mockDataBase;
    private LiveData<TaskEntry> mTask;
    private int taskId = 2;
    MutableLiveData<TaskEntry> mutableLiveData = new MutableLiveData<>();


    @Mock
    Observer<String> observer;

    @Before
    public void setUp() throws Exception {

        mutableLiveData.postValue(new TaskEntry("dummy",1, new Date()));



        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(taskDao).when(mockDataBase).taskDao();
        Mockito.doReturn(mutableLiveData).when(taskDao).loadTaskById(taskId);
        addTaskViewModel = new AddTaskViewModel(mockDataBase,taskId);
    }

    @Test
    public void getTaskEntry() {
        assertEquals(mutableLiveData, addTaskViewModel.getTaskEntry());
        assertEquals(mutableLiveData.getValue().getDescription(),"dummy");
    }

    @Test
    public void setNewValueOptionOne() {
        String input = "hello";

        addTaskViewModel.liveData2.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                addTaskViewModel.liveData2.removeObserver(this);
            }
        });

        addTaskViewModel.setNewValue(input);


        assertEquals(addTaskViewModel.liveData1.getValue(),input);
        assertEquals(addTaskViewModel.liveData2.getValue(),input.toUpperCase());
    }

    @Test
    public void setNewValueOptionTwo() throws InterruptedException {
        String input = "hello";

        //addTaskViewModel._liveData.postValue(input); //make it public
        addTaskViewModel.setNewValue(input);

        assertEquals(LiveDataTestUtil.getOrAwaitValue(addTaskViewModel.liveData1),input);
        assertEquals(LiveDataTestUtil.getOrAwaitValue(addTaskViewModel.liveData2),input.toUpperCase());
    }

    @Test
    public void setNewValueOptionThreeVerifyObserver() throws InterruptedException {
        String input = "hello";
        addTaskViewModel.liveData2.observeForever(observer);

        addTaskViewModel.setNewValue(input);

        assertEquals(addTaskViewModel.liveData1.getValue(),input);
        //verifies observer
        verify(observer).onChanged(input.toUpperCase());
        assertEquals(addTaskViewModel.liveData2.getValue(),input.toUpperCase());
//        Context context = InstrumentationRegistry.getTargetContext();
    }
}