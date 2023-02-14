package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem


import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

    //    TODO: Add testing implementation to the RemindersDao.kt
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: RemindersDatabase

    @Before
    fun iniDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).build()
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun insertAndGetReminder() = runBlockingTest {
        //GIVEN Save Reminder
        val reminder1 = ReminderDTO("reminder1", "reminder111", "ee", 0.0, 0.0)
        database.reminderDao().saveReminder(reminder1)
        //WHEN - Get Reminder By Id
        val reminderFromDb = database.reminderDao().getReminderById(reminder1.id)
        //THEN Compare Value to Insure its Correct
        assertThat<ReminderDTO>(reminderFromDb as ReminderDTO, notNullValue())
        assertThat(reminderFromDb.id, `is`(reminder1.id))
        assertThat(reminderFromDb.title, `is`(reminder1.title))
        assertThat(reminderFromDb.description, `is`(reminder1.description))
        assertThat(reminderFromDb.location, `is`(reminder1.location))
        assertThat(reminderFromDb.latitude, `is`(reminder1.latitude))
        assertThat(reminderFromDb.longitude, `is`(reminder1.longitude))
    }
}