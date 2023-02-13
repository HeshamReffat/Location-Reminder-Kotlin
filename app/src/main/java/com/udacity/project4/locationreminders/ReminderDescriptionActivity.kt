package com.udacity.project4.locationreminders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityReminderDescriptionBinding
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.utils.sendNotification
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

/**
 * Activity that displays the reminder details after the user clicks on the notification
 */
class ReminderDescriptionActivity : AppCompatActivity() {
    val job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)
    val remindersLocalRepository: ReminderDataSource by inject()

    companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        //        receive the reminder object after the user clicks on the notification
        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, ReminderDescriptionActivity::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }
    }

    private lateinit var binding: ActivityReminderDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_reminder_description
        )
        val desc = getReminderDetails()?.description
        Log.e("ReminderDescription", "${getReminderDetails()?.description}")
        binding.reminderDetailsText.text = desc
//        TODO: Add the implementation of the reminder details
    }

    fun getReminderDetails(): ReminderDataItem? {
        var reminderData: ReminderDataItem? = null
        val reminderIntentData =
            intent.getSerializableExtra(EXTRA_ReminderDataItem) as ReminderDataItem
        Log.e("ReminderDescription", reminderIntentData.id)
        scope.launch {
            val result = remindersLocalRepository.getReminder(reminderIntentData.id)
            if (result is Result.Success<ReminderDTO>) {
                val reminderDTO = result.data
                reminderData = ReminderDataItem(
                    reminderDTO.title,
                    reminderDTO.description,
                    reminderDTO.location,
                    reminderDTO.latitude,
                    reminderDTO.longitude,
                    reminderDTO.id
                )
            }
        }
        return reminderData
    }
}
