package com.udacity.project4.locationreminders.reminderslist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.IsEqual
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var remindersListViewModel: RemindersListViewModel
    val reminder1 = ReminderDTO("reminder1", "reminder111", "ee", 0.0, 0.0)
    val reminder2 = ReminderDTO("reminder2", "reminder222", "ee", 0.0, 0.0)
    private val localRemindersList = listOf(reminder1, reminder2).sortedBy { it.id }

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        fakeDataSource = FakeDataSource(localRemindersList.toMutableList())
        remindersListViewModel =
            RemindersListViewModel(ApplicationProvider.getApplicationContext(), fakeDataSource)
    }
    @After
    fun closeKoin() {
        stopKoin()
    }
    @Test
    fun getRemindersAndShowLoading() {
        mainCoroutineRule.pauseDispatcher()

        remindersListViewModel.loadReminders()

        assertThat(remindersListViewModel.showLoading.getOrAwaitValue(), IsEqual(true))

        mainCoroutineRule.resumeDispatcher()

        assertThat(remindersListViewModel.showLoading.getOrAwaitValue(), IsEqual(false))

    }
    @Test
    fun remainderListNotEmpty() = mainCoroutineRule.runBlockingTest  {
        val reminder1 = ReminderDTO("reminder1", "reminder111", "ee", 0.0, 0.0)

        fakeDataSource.saveReminder(reminder1)
        remindersListViewModel.loadReminders()

        assertThat(remindersListViewModel.remindersList.getOrAwaitValue(),(not(emptyList())))
    }
    @Test
    fun get_All_Reminders() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
        fakeDataSource.setReturnError(true)
        remindersListViewModel.loadReminders()
        mainCoroutineRule.resumeDispatcher()
        assertThat(remindersListViewModel.showSnackBar.getOrAwaitValue(),IsEqual("Error reminders test"))
    }
}