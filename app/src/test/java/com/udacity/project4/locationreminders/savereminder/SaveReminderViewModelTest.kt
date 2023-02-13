package com.udacity.project4.locationreminders.savereminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var saveReminderViewModel: SaveReminderViewModel
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        fakeDataSource = FakeDataSource()
        saveReminderViewModel = SaveReminderViewModel(ApplicationProvider.getApplicationContext(), fakeDataSource)
    }

    @Test
    fun save_Reminder() = mainCoroutineRule.runBlockingTest {
        val reminder1 = ReminderDataItem("reminder1", "reminder111", "ee", 0.0, 0.0)
        saveReminderViewModel.validateAndSaveReminder(reminder1)
//        Assert.assertThat(getReminder.data, IsEqual(reminder1))
    }
    //TODO: provide testing to the SaveReminderView and its live data objects


}