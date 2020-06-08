package com.example.todolist_java.database;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todolist_java.LiveDataTestUtil;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

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
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
        dao = database.taskDao();
    }

    @Test
    public void insertTask() throws Exception {
        int initialSize = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).size();

        // given
        TaskEntry task1 = new TaskEntry("task one", 1, new Date());
        TaskEntry task2 = new TaskEntry("task two", 2, new Date());

        dao.insertTask(task1);
        dao.insertTask(task2);

        List<TaskEntry> updatedList = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks());

        assertEquals(0, initialSize);
        assertEquals(updatedList.size(), 2);
        assertEquals(updatedList.get(0).getDescription(), task1.getDescription());
        assertEquals(updatedList.get(1).getDescription(), task2.getDescription());
        assertEquals(updatedList.get(0).getPriority(), task1.getPriority());
        assertEquals(updatedList.get(1).getPriority(), task2.getPriority());
    }

    @Test
    public void updateTask() throws Exception {
        String initialDescription = "initialDescription one";
        String updatedDescription = "initialDescription updated";
        TaskEntry task1 = new TaskEntry(initialDescription, 1, new Date());

        dao.insertTask(task1);

        TaskEntry afterInsert = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks()).get(0);
        int id = afterInsert.getId();
        task1.setId(id);
        task1.setDescription(updatedDescription);

        dao.updateTask(task1);

        TaskEntry afterUpdate = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadTaskById(id));

        assertEquals(afterInsert.getDescription(), initialDescription);
        assertEquals(afterUpdate.getDescription(), updatedDescription);
    }


    @Test
    public void deleteTask() throws Exception {
        String description = "this is description one";
        TaskEntry task1 = new TaskEntry(description, 1, new Date());

        dao.insertTask(task1);

        List<TaskEntry> afterInsert = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks());

        int id = afterInsert.get(0).getId();

        task1.setId(id);
        dao.deleteTask(task1);

//        TaskEntry afterDelete = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadTaskById(id));
        List<TaskEntry> afterDelete = LiveDataTestUtil.getOrAwaitValue(database.taskDao().loadAllTasks());
        assertEquals(afterInsert.size(), 1);
        assertEquals(afterDelete.size(),0);
//        assertEquals(afterDelete, null);
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