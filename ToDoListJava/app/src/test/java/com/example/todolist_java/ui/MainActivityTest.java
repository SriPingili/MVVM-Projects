package com.example.todolist_java.ui;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;
    private Intent intent;
    private MainActivity spyActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class, intent).create().get();
        spyActivity = spy(mainActivity);
    }

    @Test
    public void onItemClickListener() {

        spyActivity.onItemClickListener(5);



        verify(spyActivity).startActivity(any(Intent.class));
    }
}