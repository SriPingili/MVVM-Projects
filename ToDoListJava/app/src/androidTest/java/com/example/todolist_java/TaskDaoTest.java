package com.example.todolist_java;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todolist_java.database.AppDatabase;
import com.example.todolist_java.database.dao.TaskDao;
import com.example.todolist_java.database.entity.TaskEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private AppDatabase database;
    private TaskDao dao;

    @Mock
    private Observer<List<TaskEntry>> observer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = database.taskDao();
    }

    @Test
    public void insertTask() throws Exception {
        // given
        TaskEntry task1 = new TaskEntry("task one", 1, new Date());
        TaskEntry task2 = new TaskEntry("task two", 2, new Date());

        dao.insertTask(task1);
        dao.insertTask(task2);

        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).size(), 2);
        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(0).getDescription(), task1.getDescription());
        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(1).getDescription(), task2.getDescription());
        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(0).getPriority(), task1.getPriority());
        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(1).getPriority(), task2.getPriority());

//        verify(observer).onChanged(Collections.singletonList(task1));
    }

    @Test
    public void updateTask() throws Exception {
        String initialDescription = "initialDescription one";
        String updatedDescription = "initialDescription updated";
        TaskEntry task1 = new TaskEntry(initialDescription, 1, new Date());

        dao.insertTask(task1);

        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).size(), 1);

        int id = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(0).getId();
        task1.setId(id);
        task1.setDescription(updatedDescription);

        dao.updateTask(task1);

        String currentDescription = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(0).getDescription();
        assertEquals(currentDescription, updatedDescription);
    }


    @Test
    public void deleteTask() throws Exception {
        String description = "this is description one";
        TaskEntry task1 = new TaskEntry(description, 1, new Date());

        dao.insertTask(task1);

        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).size(), 1);

        int id = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(0).getId();

        task1.setId(id);
        dao.deleteTask(task1);

        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).size(), 0);
    }

    @Test
    public void loadTaskById() throws Exception {
        String description = "this is description one";
        TaskEntry task1 = new TaskEntry(description, 1, new Date());

        dao.insertTask(task1);

        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).size(), 1);

        int id = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(0).getId();


        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadTaskById(id)).getDescription(), task1.getDescription());
        assertEquals(LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadTaskById(id)).getPriority(), task1.getPriority());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }
}