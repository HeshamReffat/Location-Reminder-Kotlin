package com.udacity.project4.locationreminders.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import kotlinx.coroutines.runBlocking

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource :
    ReminderDataSource {

    //    TODO: Create a fake data source to act as a double to the real data source
    private var shouldReturnError = false

    var reminders: MutableList<ReminderDTO>? = mutableListOf()

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if (shouldReturnError) {
            return Result.Error(
                "Test exception"
            )
        }
        reminders?.let {
            return Result.Success(ArrayList(it))
        }
        return Result.Error(
            "No Data"
        )
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {

        reminders?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        val r = reminders?.find { it.id == id }
        return Result.Success(r!!)
    }

    override suspend fun deleteAllReminders() {
        reminders?.clear()
    }

}